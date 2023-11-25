package project.fashionecommerce.backend.fashionecommerceproject.service.database.stock;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import project.fashionecommerce.backend.fashionecommerceproject.config.security.userDetails.Implement.UserDetailsImpl;
import project.fashionecommerce.backend.fashionecommerceproject.dto.enums.ERole;
import project.fashionecommerce.backend.fashionecommerceproject.dto.product.ProductId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.review.Review;
import project.fashionecommerce.backend.fashionecommerceproject.dto.review.ReviewMapper;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.Stock;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.StockId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.StockMapper;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.StockQuery;
import project.fashionecommerce.backend.fashionecommerceproject.exception.MyForbiddenException;
import project.fashionecommerce.backend.fashionecommerceproject.exception.MyResourceNotFoundException;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.stock.StockEntity;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.stock.StockRepository;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.user.UserEntity;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.user.UserRepository;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StockQueryService {
    @NonNull final StockRepository stockRepository;
    private final UserRepository userRepository;

    @NonNull final MongoTemplate mongoTemplate;

    @NonNull final StockMapper stockMapper;
    @NonNull final ReviewMapper reviewMapper;

    public Page<Stock> findAll(StockQuery stockQuery, PageRequest pageRequest, ERole role) {
        Criteria criteria = new Criteria();

        if (stockQuery.search() != null && !stockQuery.search().isBlank()) {
            criteria.orOperator(
                    Criteria.where("products.name").regex(".*" + stockQuery.search() + ".*", "i"),
                    Criteria.where("products.description").regex(".*" + stockQuery.search() + ".*", "i")
            );
        }

        Optional<List<String>> sizeIds = Optional.ofNullable(stockQuery.sizeIds());
        if (!sizeIds.isEmpty() && !sizeIds.get().isEmpty()) {
            criteria.and("sizeId").in(sizeIds.get().stream().map(sizeId -> new ObjectId(sizeId)).collect(Collectors.toList()));
        }

        Optional<List<String>> colorIds = Optional.ofNullable(stockQuery.colorIds());
        if (!colorIds.isEmpty() && !colorIds.get().isEmpty()) {
            criteria.and("colorId").in(colorIds.get().stream().map(colorId -> new ObjectId(colorId)).collect(Collectors.toList()));
        }

        if (role.equals(ERole.STAFF)){
            criteria.and("isActive").is(false);
            criteria.and("isDeleted").is(false);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            criteria.and("createdBy").is(new ObjectId(userDetails.getId()));
        }

        Aggregation countAggregation = Aggregation.newAggregation(
                Aggregation.lookup("product", "productId", "_id", "products"),
                Aggregation.unwind("products", true),
                Aggregation.match(criteria),
                Aggregation.group().count().as("totalRecords")
        );

        AggregationResults<Map> countResults = mongoTemplate.aggregate(countAggregation, "stock", Map.class);
        Long total = countResults.getMappedResults().size() == 0 ? 0 : Long.parseLong(countResults.getMappedResults().get(0).get("totalRecords").toString());

        int currentPage = pageRequest.getPageNumber();
        int totalPages = (int) Math.ceil((double) total / pageRequest.getPageSize());
        if (currentPage > totalPages) {
            currentPage = totalPages - 1;
        }

        Aggregation mainAggregation = Aggregation.newAggregation(
                Aggregation.lookup("product", "productId", "_id", "products"),
                Aggregation.unwind("products", true),
                Aggregation.match(criteria),
                Aggregation.skip(currentPage * pageRequest.getPageSize()),
                Aggregation.limit(pageRequest.getPageSize())
        );

        PageRequest newPageRequest = PageRequest.of(currentPage, pageRequest.getPageSize(), pageRequest.getSort());

        AggregationResults<StockEntity> results = mongoTemplate.aggregate(mainAggregation, "stock", StockEntity.class);
        List<StockEntity> stockList = results.getMappedResults();

        List<StockEntity> pagedStockList = stockList.subList(0, Math.min(pageRequest.getPageSize(), stockList.size()));
        Page<StockEntity> stockPage = PageableExecutionUtils.getPage(pagedStockList, newPageRequest, () -> total);
        return new PageImpl<>(stockPage.getContent().stream().map(stockMapper::toDto).collect(Collectors.toList()), pageRequest, total);
    }


    public Stock findById(StockId stockId) {
        StockEntity stockEntity = stockRepository.findById(stockId.id())
                .orElseThrow(MyResourceNotFoundException::new);
        return stockMapper.toDto(stockEntity);
    }

    public List<Stock> findAllByProductId(ProductId productId) {
        List<StockEntity> stockEntities = stockRepository.findAllByProductId(productId.id());
        List<Stock> stocks = stockEntities.stream().map(stockMapper::toDto).collect(Collectors.toList());
        stocks = stocks.stream().map(stock -> {
            List<Review> reviews = stock.reviews();
            reviews = reviews.stream().map(review -> {
                UserEntity userEntity = userRepository.findById(review.userId()).orElseThrow(MyResourceNotFoundException::new);
                String username = userEntity.getFirstName() + " " + userEntity.getLastName();
                return reviewMapper.updateDto(review, username);
            }).collect(Collectors.toList());
            return stockMapper.updateDto(stock, reviews);
        }).collect(Collectors.toList());
        return stocks;
    }
}

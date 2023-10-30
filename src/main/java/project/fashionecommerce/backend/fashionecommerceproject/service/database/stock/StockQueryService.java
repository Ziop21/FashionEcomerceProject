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
import org.springframework.stereotype.Service;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.Stock;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.StockId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.StockMapper;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.StockQuery;
import project.fashionecommerce.backend.fashionecommerceproject.exception.MyResourceNotFoundException;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.stock.StockEntity;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.stock.StockRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StockQueryService {
    @NonNull final StockRepository stockRepository;
    @NonNull final MongoTemplate mongoTemplate;
    @NonNull final StockMapper stockMapper;
    public Page<Stock> findAll(StockQuery stockQuery, PageRequest pageRequest) {
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
            criteria.and("colorIds").in(colorIds.get().stream().map(colorId -> new ObjectId(colorId)).collect(Collectors.toList()));
        }

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.lookup("product", "productId", "_id", "products"),
                Aggregation.unwind("products", true),
                Aggregation.match(criteria),
                Aggregation.skip(pageRequest.getPageNumber() * pageRequest.getPageSize()),
                Aggregation.limit(pageRequest.getPageSize())
        );

        AggregationResults<StockEntity> results = mongoTemplate.aggregate(aggregation, "stock", StockEntity.class);

        List<StockEntity> stockList = results.getMappedResults();

        Long total = (long) stockList.size();

        List<StockEntity> pagedStockList = stockList.subList(0, Math.min(pageRequest.getPageSize(), stockList.size()));
        Page<StockEntity> stockPage = PageableExecutionUtils.getPage(pagedStockList, pageRequest, () -> total);

        return new PageImpl<>(stockPage.getContent().stream().map(stockMapper::toDto).collect(Collectors.toList()), pageRequest, total);
    }


    public Stock findById(StockId stockId) {
        StockEntity stockEntity = stockRepository.findById(stockId.id())
                .orElseThrow(MyResourceNotFoundException::new);
        return stockMapper.toDto(stockEntity);
    }
}

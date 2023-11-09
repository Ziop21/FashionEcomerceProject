package project.fashionecommerce.backend.fashionecommerceproject.service.database.product;

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
import project.fashionecommerce.backend.fashionecommerceproject.dto.enums.ERole;
import project.fashionecommerce.backend.fashionecommerceproject.dto.product.Product;
import project.fashionecommerce.backend.fashionecommerceproject.dto.product.ProductId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.product.ProductMapper;
import project.fashionecommerce.backend.fashionecommerceproject.dto.product.ProductQuery;
import project.fashionecommerce.backend.fashionecommerceproject.exception.MyResourceNotFoundException;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.product.ProductEntity;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.product.ProductRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductQueryService {
    @NonNull final ProductRepository productRepository;
    @NonNull final ProductMapper productMapper;
    @NonNull final MongoTemplate mongoTemplate;
    public Product findById(ProductId productId) {
        ProductEntity product = productRepository.findById(productId.id()).orElseThrow(MyResourceNotFoundException::new);
        return productMapper.toDto(product);
    }

    public Page<Product> findAll(ProductQuery productQuery, PageRequest pageRequest, ERole role) {
        Criteria criteria = new Criteria();

        if (productQuery.search() != null && !productQuery.search().isBlank()) {
            criteria.orOperator(
                    Criteria.where("name").regex(".*" + productQuery.search() + ".*", "i"),
                    Criteria.where("slug").regex(".*" + productQuery.search() + ".*", "i"),
                    Criteria.where("description").regex(".*" + productQuery.search() + ".*", "i")
            );
        }

        Optional<List<String>> sizeIds = Optional.ofNullable(productQuery.sizeIds());
        if (!sizeIds.isEmpty() && !sizeIds.get().isEmpty()) {
            criteria.and("stocks.sizeId").in(sizeIds.get().stream().map(sizeId -> new ObjectId(sizeId)).collect(Collectors.toList()));
        }

        Optional<List<String>> colorIds = Optional.ofNullable(productQuery.colorIds());
        if (!colorIds.isEmpty() && !colorIds.get().isEmpty()) {
            criteria.and("stocks.colorId").in(colorIds.get().stream().map(colorId -> new ObjectId(colorId)).collect(Collectors.toList()));
        }

        if (productQuery.fromRating() != null && productQuery.toRating() != null){
            criteria.and("rating").gte(productQuery.fromRating()).lte(productQuery.toRating());
        }

        if (productQuery.fromPrice() != null && productQuery.toPrice() != null){
            criteria.and("price").gte(productQuery.fromPrice()).lte(productQuery.toPrice());
        }

        if (productQuery.fromDate() != null && productQuery.toDate() != null){
            LocalDateTime newFromDate = LocalDateTime.parse(productQuery.fromDate() + "T00:00:00");
            LocalDateTime newToDate = LocalDateTime.parse(productQuery.toDate() + "T23:59:59");
            criteria.and("createdAt").gte(newFromDate).lte(newToDate);
        }

        if (role.equals(ERole.GUEST) || role.equals(ERole.CUSTOMER)){
            criteria.and("isDeleted").is(false);
            criteria.and("isActive").is(true);
            criteria.and("isSelling").is(true);
        }

        if (role.equals(ERole.STAFF)){
            criteria.and("isDeleted").is(false);
            criteria.and("isActive").is(false);
            criteria.and("isSelling").is(false);
        }

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.lookup("stock", "_id", "productId", "stocks"),
                Aggregation.unwind("stocks", true),
                Aggregation.match(criteria),
                Aggregation.group("_id")
                        .first("name").as("name")
                        .first("slug").as("slug")
                        .first("description").as("description")
                        .first("price").as("price")
                        .first("promotionalPrice").as("promotionalPrice")
                        .first("view").as("view")
                        .first("isSelling").as("isSelling")
                        .first("images").as("images")
                        .first("rating").as("rating")
                        .first("isDeleted").as("isDeleted")
                        .first("isActive").as("isActive")
                        .first("createdAt").as("createdAt")
                        .first("updatedAt").as("updatedAt")
                        .first("createdBy").as("createdBy")
                        .first("updatedBy").as("updatedBy")
                ,
                Aggregation.skip(pageRequest.getPageNumber() * pageRequest.getPageSize()),
                Aggregation.limit(pageRequest.getPageSize())
        );

        AggregationResults<ProductEntity> results = mongoTemplate.aggregate(aggregation, "product", ProductEntity.class);

        List<ProductEntity> productList = results.getMappedResults();

        Long total = (long) productList.size();

        List<ProductEntity> pagedProductList = productList.subList(0, Math.min(pageRequest.getPageSize(), productList.size()));
        Page<ProductEntity> productPage = PageableExecutionUtils.getPage(pagedProductList, pageRequest, () -> total);

        return new PageImpl<>(productPage.getContent().stream().map(productMapper::toDto).collect(Collectors.toList()), pageRequest, total);
    }

    public List<Product> findAllByIds(List<ProductId> productIds) {
        Criteria criteria = new Criteria();

        criteria.and("isDeleted").is(false);
        criteria.and("isActive").is(true);
        criteria.and("isSelling").is(true);

        if (productIds != null && !productIds.isEmpty()){
            List<ObjectId> objectIds = productIds.stream().map(productId -> new ObjectId(productId.id())).collect(Collectors.toList());
            criteria.and("_id").in(objectIds);
        }

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(criteria)
        );

        AggregationResults<ProductEntity> results = mongoTemplate.aggregate(aggregation, "product", ProductEntity.class);

        List<ProductEntity> productList = results.getMappedResults();

        return productList.stream().map(productMapper::toDto).collect(Collectors.toList());
    }
}

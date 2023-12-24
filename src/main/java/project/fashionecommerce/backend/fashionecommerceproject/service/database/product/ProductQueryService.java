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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import project.fashionecommerce.backend.fashionecommerceproject.config.security.userDetails.Impl.UserDetailsImpl;
import project.fashionecommerce.backend.fashionecommerceproject.dto.enums.ERole;
import project.fashionecommerce.backend.fashionecommerceproject.dto.product.Product;
import project.fashionecommerce.backend.fashionecommerceproject.dto.product.ProductId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.product.ProductMapper;
import project.fashionecommerce.backend.fashionecommerceproject.dto.product.ProductQuery;
import project.fashionecommerce.backend.fashionecommerceproject.exception.MyResourceNotFoundException;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.product.ProductEntity;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.product.ProductRepository;
import java.util.List;
import java.util.Map;
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

    public List<ProductId> findAllProductIds(ProductQuery productQuery, PageRequest pageRequest, ERole role){
        Criteria criteria = new Criteria();

        if (productQuery.search() != null && !productQuery.search().isBlank()) {
            criteria.orOperator(
                    Criteria.where("name").regex(".*" + productQuery.search() + ".*", "i"),
                    Criteria.where("slug").regex(".*" + productQuery.search() + ".*", "i"),
                    Criteria.where("description").regex(".*" + productQuery.search() + ".*", "i")
            );
        }
        Optional<List<String>> categoryIds = Optional.ofNullable(productQuery.categoryIds());
        if (!categoryIds.isEmpty() && !categoryIds.get().isEmpty()) {
            criteria.and("categoryProduct.categoryId").in(categoryIds.get().stream().map(categoryId -> new ObjectId(categoryId)).collect(Collectors.toList()));
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

        if (role.equals(ERole.GUEST) || role.equals(ERole.CUSTOMER)){
            criteria.and("isDeleted").is(false);
            criteria.and("isActive").is(true);
            criteria.and("isSelling").is(true);
        }

        if (role.equals(ERole.STAFF)){
            criteria.and("isDeleted").is(false);
            criteria.and("isActive").is(false);
            criteria.and("isSelling").is(false);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            criteria.and("createdBy").is(new ObjectId(userDetails.getId()));
        }

        Aggregation countAggregation = Aggregation.newAggregation(
                Aggregation.lookup("stock", "_id", "productId", "stocks"),
                Aggregation.unwind("stocks", true),
                Aggregation.lookup("category_product", "_id", "productId", "categoryProduct"),
                Aggregation.unwind("categoryProduct", true),
                Aggregation.match(criteria),
                Aggregation.group("_id")
        );

        AggregationResults<ProductId> results = mongoTemplate.aggregate(countAggregation, "product", ProductId.class);
        List<ProductId> productIdList = results.getMappedResults();

        return productIdList;
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
        Optional<List<String>> categoryIds = Optional.ofNullable(productQuery.categoryIds());
        if (!categoryIds.isEmpty() && !categoryIds.get().isEmpty()) {
            criteria.and("categoryProduct.categoryId").in(categoryIds.get().stream().map(categoryId -> new ObjectId(categoryId)).collect(Collectors.toList()));
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

        if (role.equals(ERole.GUEST) || role.equals(ERole.CUSTOMER)){
            criteria.and("isDeleted").is(false);
            criteria.and("isActive").is(true);
            criteria.and("isSelling").is(true);
        }

        if (role.equals(ERole.STAFF)){
            criteria.and("isDeleted").is(false);
            criteria.and("isActive").is(false);
            criteria.and("isSelling").is(false);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            criteria.and("createdBy").is(new ObjectId(userDetails.getId()));
        }

        Aggregation countAggregation = Aggregation.newAggregation(
                Aggregation.lookup("stock", "_id", "productId", "stocks"),
                Aggregation.unwind("stocks", true),
                Aggregation.lookup("category_product", "_id", "productId", "categoryProduct"),
                Aggregation.unwind("categoryProduct", true),
                Aggregation.match(criteria),
                Aggregation.group("_id"),
//                        .first("name").as("name")
//                        .first("slug").as("slug")
//                        .first("description").as("description")
//                        .first("price").as("price")
//                        .first("promotionalPrice").as("promotionalPrice")
//                        .first("view").as("view")
//                        .first("isSelling").as("isSelling")
//                        .first("images").as("images")
//                        .first("rating").as("rating")
//                        .first("isDeleted").as("isDeleted")
//                        .first("isActive").as("isActive")
//                        .first("createdAt").as("createdAt")
//                        .first("updatedAt").as("updatedAt")
//                        .first("createdBy").as("createdBy")
//                        .first("updatedBy").as("updatedBy"),
                Aggregation.group().count().as("totalRecords")
        );

        AggregationResults<Map> countResults = mongoTemplate.aggregate(countAggregation, "product", Map.class);
        Long total = countResults.getMappedResults().size() == 0 ? 0 : Long.parseLong(countResults.getMappedResults().get(0).get("totalRecords").toString());

        int currentPage = pageRequest.getPageNumber();
        int totalPages = (int) Math.ceil((double) total / pageRequest.getPageSize());
        if (currentPage > totalPages) {
            currentPage = totalPages - 1;
        }

        Aggregation mainAggregation = Aggregation.newAggregation(
                Aggregation.lookup("stock", "_id", "productId", "stocks"),
                Aggregation.unwind("stocks", true),
                Aggregation.lookup("category_product", "_id", "productId", "categoryProduct"),
                Aggregation.unwind("categoryProduct", true),
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
                        .first("updatedBy").as("updatedBy"),
                Aggregation.sort(pageRequest.getSort()),
                Aggregation.skip(currentPage * pageRequest.getPageSize()),
                Aggregation.limit(pageRequest.getPageSize())
        );

        PageRequest newPageRequest = PageRequest.of(currentPage, pageRequest.getPageSize(), pageRequest.getSort());

        AggregationResults<ProductEntity> results = mongoTemplate.aggregate(mainAggregation, "product", ProductEntity.class);
        List<ProductEntity> productList = results.getMappedResults();

        List<ProductEntity> pagedProductList = productList.subList(0, Math.min(pageRequest.getPageSize(), productList.size()));
        Page<ProductEntity> productPage = PageableExecutionUtils.getPage(pagedProductList, newPageRequest, () -> total);
        
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

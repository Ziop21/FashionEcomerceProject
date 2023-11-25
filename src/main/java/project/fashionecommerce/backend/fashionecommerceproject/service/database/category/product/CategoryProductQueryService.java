package project.fashionecommerce.backend.fashionecommerceproject.service.database.category.product;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import project.fashionecommerce.backend.fashionecommerceproject.dto.category.product.CategoryProduct;
import project.fashionecommerce.backend.fashionecommerceproject.exception.MyResourceNotFoundException;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.category.product.*;
import project.fashionecommerce.backend.fashionecommerceproject.dto.category.product.CategoryProductId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.category.product.CategoryProductQuery;
import project.fashionecommerce.backend.fashionecommerceproject.dto.category.product.CategoryProductMapper;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryProductQueryService {
    @NonNull
    final CategoryProductRepository categoryProductRepository;
    @NonNull
    final CategoryProductMapper categoryProductMapper;
    @NonNull
    final MongoTemplate mongoTemplate;

    public CategoryProduct findById(CategoryProductId categoryProductId) {
        CategoryProductEntity categoryProductEntity = categoryProductRepository.findById(categoryProductId.id())
                .orElseThrow(MyResourceNotFoundException::new);
        return categoryProductMapper.toDto(categoryProductEntity);
    }

    public Page<CategoryProduct> findAll(CategoryProductQuery categoryProductQuery, PageRequest pageRequest) {
        Criteria criteria = new Criteria();

        if (categoryProductQuery.search() != null && !categoryProductQuery.search().isBlank()) {
            criteria.orOperator(
                    Criteria.where("user.firstName").regex(".*" + categoryProductQuery.search() + ".*", "i"),
                    Criteria.where("user.lastName").regex(".*" + categoryProductQuery.search() + ".*", "i"),
                    Criteria.where("user.email").regex(".*" + categoryProductQuery.search() + ".*", "i"),
                    Criteria.where("category.name").regex(".*" + categoryProductQuery.search() + ".*", "i"),
                    Criteria.where("product.name").regex(".*" + categoryProductQuery.search() + ".*", "i"),
                    Criteria.where("product.description").regex(".*" + categoryProductQuery.search() + ".*", "i")
            );
        }

        if (categoryProductQuery.fromDate() != null && categoryProductQuery.toDate() != null){
            LocalDateTime newFromDate = LocalDateTime.parse(categoryProductQuery.fromDate() + "T00:00:00");
            LocalDateTime newToDate = LocalDateTime.parse(categoryProductQuery.toDate() + "T23:59:59");
            criteria.and("createdAt").gte(newFromDate).lte(newToDate);
        }

//        Aggregation aggregation = Aggregation.newAggregation(
//                Aggregation.lookup("user", "createdBy", "_id", "user"),
//                Aggregation.unwind("user", true),
//                Aggregation.lookup("product", "productId", "_id", "product"),
//                Aggregation.unwind("product", true),
//                Aggregation.lookup("category", "categoryId", "_id", "category"),
//                Aggregation.unwind("category", true),
//                Aggregation.match(criteria),
//                Aggregation.skip(pageRequest.getPageNumber() * pageRequest.getPageSize()),
//                Aggregation.limit(pageRequest.getPageSize())
//        );
//
//        AggregationResults<CategoryProductEntity> results = mongoTemplate.aggregate(aggregation, "category_product", CategoryProductEntity.class);
//
//        List<CategoryProductEntity> categoryProductList = results.getMappedResults();
//
//        Long total = (long) categoryProductList.size();
//
//        List<CategoryProductEntity> pagedCategoryProductList = categoryProductList.subList(0, Math.min(pageRequest.getPageSize(), categoryProductList.size()));
//        Page<CategoryProductEntity> categoryProductPage = PageableExecutionUtils.getPage(pagedCategoryProductList, pageRequest, () -> total);
        Aggregation countAggregation = Aggregation.newAggregation(
                Aggregation.lookup("user", "createdBy", "_id", "user"),
                Aggregation.unwind("user", true),
                Aggregation.lookup("product", "productId", "_id", "product"),
                Aggregation.unwind("product", true),
                Aggregation.lookup("category", "categoryId", "_id", "category"),
                Aggregation.unwind("category", true),
                Aggregation.match(criteria),
                Aggregation.group().count().as("totalRecords")
        );

        AggregationResults<Map> countResults = mongoTemplate.aggregate(countAggregation, "category_product", Map.class);

        Long total = countResults.getMappedResults().size() == 0 ? 0 : Long.parseLong(countResults.getMappedResults().get(0).get("totalRecords").toString());

        int currentPage = pageRequest.getPageNumber();
        int totalPages = (int) Math.ceil((double) total / pageRequest.getPageSize());
        if (currentPage > totalPages) {
            currentPage = totalPages - 1;
        }

        Aggregation mainAggregation = Aggregation.newAggregation(
                Aggregation.lookup("user", "createdBy", "_id", "user"),
                Aggregation.unwind("user", true),
                Aggregation.lookup("product", "productId", "_id", "product"),
                Aggregation.unwind("product", true),
                Aggregation.lookup("category", "categoryId", "_id", "category"),
                Aggregation.unwind("category", true),
                Aggregation.match(criteria),
                Aggregation.skip(currentPage * pageRequest.getPageSize()),
                Aggregation.limit(pageRequest.getPageSize())
        );

        PageRequest newPageRequest = PageRequest.of(currentPage, pageRequest.getPageSize(), pageRequest.getSort());

        AggregationResults<CategoryProductEntity> results = mongoTemplate.aggregate(mainAggregation, "category_product", CategoryProductEntity.class);
        List<CategoryProductEntity> categoryProductList = results.getMappedResults();

        List<CategoryProductEntity> pagedCategoryProductList = categoryProductList.subList(0, Math.min(pageRequest.getPageSize(), categoryProductList.size()));
        Page<CategoryProductEntity> categoryProductPage = PageableExecutionUtils.getPage(pagedCategoryProductList, newPageRequest, () -> total);
        return new PageImpl<>(categoryProductPage.getContent().stream().map(categoryProductMapper::toDto).collect(Collectors.toList()), pageRequest, total);

    }
}

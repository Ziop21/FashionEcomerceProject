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

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.lookup("user", "createdBy", "_id", "user"),
                Aggregation.unwind("user"),
                Aggregation.lookup("product", "productId", "_id", "product"),
                Aggregation.unwind("product"),
                Aggregation.lookup("category", "categoryId", "_id", "category"),
                Aggregation.unwind("category"),
                Aggregation.match(criteria),
                Aggregation.skip(pageRequest.getPageNumber() * pageRequest.getPageSize()),
                Aggregation.limit(pageRequest.getPageSize())
        );

        AggregationResults<CategoryProductEntity> results = mongoTemplate.aggregate(aggregation, "category_product", CategoryProductEntity.class);

        List<CategoryProductEntity> categoryProductList = results.getMappedResults();

        Long total = (long) categoryProductList.size();

        List<CategoryProductEntity> pagedCategoryProductList = categoryProductList.subList(0, Math.min(pageRequest.getPageSize(), categoryProductList.size()));
        Page<CategoryProductEntity> categoryProductPage = PageableExecutionUtils.getPage(pagedCategoryProductList, pageRequest, () -> total);

        return new PageImpl<>(categoryProductPage.getContent().stream().map(categoryProductMapper::toDto).collect(Collectors.toList()), pageRequest, total);

    }
}

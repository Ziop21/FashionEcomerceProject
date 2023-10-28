package project.fashionecommerce.backend.fashionecommerceproject.service.database.product;

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
import project.fashionecommerce.backend.fashionecommerceproject.dto.product.Product;
import project.fashionecommerce.backend.fashionecommerceproject.dto.product.ProductId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.product.ProductMapper;
import project.fashionecommerce.backend.fashionecommerceproject.dto.product.ProductQuery;
import project.fashionecommerce.backend.fashionecommerceproject.exception.MyResourceNotFoundException;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.product.ProductEntity;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.product.ProductRepository;
import java.time.LocalDateTime;
import java.util.List;
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

    public Page<Product> findAll(ProductQuery productQuery, PageRequest pageRequest) {
        Criteria criteria = new Criteria();

        if (productQuery.search() != null && !productQuery.search().isBlank()) {
            criteria.orOperator(
                    Criteria.where("name").regex(".*" + productQuery.search() + ".*", "i"),
                    Criteria.where("slug").regex(".*" + productQuery.search() + ".*", "i"),
                    Criteria.where("description").regex(".*" + productQuery.search() + ".*", "i")
            );
        }

        if (productQuery.fromDate() != null && productQuery.toDate() != null){
            LocalDateTime newFromDate = LocalDateTime.parse(productQuery.fromDate() + "T00:00:00");
            LocalDateTime newToDate = LocalDateTime.parse(productQuery.toDate() + "T23:59:59");
            criteria.and("createdAt").gte(newFromDate).lte(newToDate);
        }

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(criteria),
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
}

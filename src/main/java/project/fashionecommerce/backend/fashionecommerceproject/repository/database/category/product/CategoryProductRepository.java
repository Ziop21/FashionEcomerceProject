package project.fashionecommerce.backend.fashionecommerceproject.repository.database.category.product;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryProductRepository extends MongoRepository<CategoryProductEntity, String> {
    Boolean existsByProductIdAndAndCategoryId(String productId, String categoryId);
}

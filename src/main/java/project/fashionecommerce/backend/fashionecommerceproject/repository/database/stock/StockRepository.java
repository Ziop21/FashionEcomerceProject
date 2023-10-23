package project.fashionecommerce.backend.fashionecommerceproject.repository.database.stock;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface StockRepository extends MongoRepository<StockEntity, String> {
    Boolean existsByProductIdAndSizeIdAndColorIds(String productId, String sizeId, List<String> colorIds);
}

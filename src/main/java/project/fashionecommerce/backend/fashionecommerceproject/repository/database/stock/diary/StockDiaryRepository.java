package project.fashionecommerce.backend.fashionecommerceproject.repository.database.stock.diary;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockDiaryRepository extends MongoRepository<StockDiaryEntity, String>{
}


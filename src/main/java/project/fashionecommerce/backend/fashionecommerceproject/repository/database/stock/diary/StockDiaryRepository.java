package project.fashionecommerce.backend.fashionecommerceproject.repository.database.stock.diary;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StockDiaryRepository extends MongoRepository<StockDiaryEntity, String>{
    @Query(value = "{ 'createdBy': { $regex: ?0, $options: 'i'} }")
    Page<StockDiaryEntity> customerFindAll(String search, Pageable pageable);
    boolean existsById(String createdBy);
}

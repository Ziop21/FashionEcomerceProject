package project.fashionecomerce.backend.fashionecomerceproject.repository.stockDiary;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface StockDiaryRepository extends MongoRepository<StockDiaryEntity, String>{
    @Query(value = "{ 'id': { $regex: ?0} }")
    Page<StockDiaryEntity> customerFindAll(String search, Pageable pageable);
    Boolean existsByName(String id);

}

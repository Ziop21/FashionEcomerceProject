package project.fashionecommerce.backend.fashionecommerceproject.repository.database.stock.diary;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface StockDiaryRepository extends MongoRepository<StockDiaryEntity, String>{
    Page<StockDiaryEntity> findAll(Pageable pageable);


    boolean existsByCreatedBy(String createdBy);
}


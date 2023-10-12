package project.fashionecomerce.backend.fashionecomerceproject.repository.stockDiary;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import project.fashionecomerce.backend.fashionecomerceproject.repository.size.SizeEntity;

@Repository
public interface StockDiaryRepository {
    @Query(value = "{ 'id': { $regex: ?0} }")
    Page<StockDiaryEntity> customerFindAll(String search, Pageable pageable);
    Boolean existsByName(String id);

    void save(StockDiaryEntity stockDiaryEntity);
}

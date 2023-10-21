package project.fashionecommerce.backend.fashionecommerceproject.service.database.stock.diary;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.diary.StockDiary;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.diary.StockDiaryId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.diary.StockDiaryMapper;
import project.fashionecommerce.backend.fashionecommerceproject.exception.MyConflictsException;
import project.fashionecommerce.backend.fashionecommerceproject.exception.MyResourceNotFoundException;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.stock.diary.StockDiaryEntity;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.stock.diary.StockDiaryRepository;

@Service
@RequiredArgsConstructor
public class StockDiaryCommandService {
    @NonNull
    final StockDiaryMapper stockDiaryMapper;
    @NonNull
    final StockDiaryRepository stockDiaryRepository;

    public void save(StockDiary stockDiary) {
            StockDiaryEntity stockDiaryEntity = stockDiaryMapper.toEntity(stockDiary);
            stockDiaryRepository.save(stockDiaryEntity);
    }

    public void delete(StockDiaryId stockDiaryId) {stockDiaryRepository.deleteById(stockDiaryId.id());
    }

    public void update(StockDiaryId stockDiaryId, StockDiary stockDiary) {
        StockDiaryEntity foundEntity = stockDiaryRepository.findById(stockDiaryId.id()).orElseThrow(MyResourceNotFoundException::new);
        stockDiaryMapper.updateExisted(stockDiary, foundEntity);
        stockDiaryRepository.save(foundEntity);
    }
}

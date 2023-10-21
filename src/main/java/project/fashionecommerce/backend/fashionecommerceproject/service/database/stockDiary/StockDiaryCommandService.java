package project.fashionecommerce.backend.fashionecommerceproject.service.database.stockDiary;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stockDiary.StockDiary;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stockDiary.StockDiaryId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stockDiary.StockDiaryMapper;
import project.fashionecommerce.backend.fashionecommerceproject.exception.MyConflictsException;
import project.fashionecommerce.backend.fashionecommerceproject.exception.MyResourceNotFoundException;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.stockDiary.StockDiaryEntity;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.stockDiary.StockDiaryRepository;

@Service
@RequiredArgsConstructor
public class StockDiaryCommandService {
    @NonNull
    final StockDiaryMapper stockDiaryMapper;
    @NonNull
    final StockDiaryRepository stockDiaryRepository;

    public void save(StockDiary stockDiary) {
            if (stockDiaryRepository.existsById(stockDiary.id()))
                throw new MyConflictsException();
            StockDiaryEntity stockDiaryEntity = stockDiaryMapper.toEntity(stockDiary);
            stockDiaryRepository.save(stockDiaryEntity);

    }

    public void delete(StockDiaryId stockDiaryId) {stockDiaryRepository.deleteById(stockDiaryId.id());
    }

    public void update(StockDiaryId stockDiaryId, StockDiary stockDiary) {
        StockDiaryEntity stockDiaryEntity = stockDiaryRepository.findById(stockDiaryId.id()).orElseThrow(MyResourceNotFoundException::new);
        if (!stockDiary.id().equals(stockDiaryEntity.getId())
                && stockDiaryRepository.existsById(stockDiary.id()))
            throw new MyConflictsException();
        stockDiaryRepository.save(stockDiaryEntity);
    }
}

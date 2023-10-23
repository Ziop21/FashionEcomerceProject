package project.fashionecommerce.backend.fashionecommerceproject.service.database.stock.diary;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.diary.StockDiary;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.diary.StockDiaryId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.diary.StockDiaryQuery;

@Service
@RequiredArgsConstructor
public class StockDiaryUseCaseService {
    @NonNull
    final StockDiaryCommandService stockDiaryCommandService;
    @NonNull
    final StockDiaryQueryService stockDiaryQueryService;
    @Transactional
    public void save(StockDiary stockDiary) {
        stockDiaryCommandService.save(stockDiary);
    }
    @Transactional
    public void update(StockDiaryId stockDiaryId, StockDiary stockDiary) {
        stockDiaryCommandService.update(stockDiaryId, stockDiary);
    }
    @Transactional
    public void delete(StockDiaryId stockDiaryId) {
        stockDiaryCommandService.delete(stockDiaryId);
    }
    public StockDiary findById(StockDiaryId stockDiaryId) {
        return stockDiaryQueryService.findById(stockDiaryId);
    }
    public Page<StockDiary> findAll( PageRequest pageRequest) {
        return stockDiaryQueryService.findAll( pageRequest);
    }
}

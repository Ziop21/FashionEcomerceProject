package project.fashionecommerce.backend.fashionecommerceproject.service.database.stockDiary;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stockDiary.StockDiary;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stockDiary.StockDiaryId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stockDiary.StockDiaryQuery;

@Service
@RequiredArgsConstructor
public class StockDiaryUseCaseService {
    @NonNull
    final StockDiaryCommandService stockDiaryCommandService;
    @NonNull
    final StockDiaryQueryService stockDiaryQueryService;
    @Transactional
    public void save(StockDiary stockDiary) {stockDiaryCommandService.save(stockDiary);}
    public Page<StockDiary> findAll(StockDiaryQuery stockDiaryQuery, PageRequest pageRequest) {
            return stockDiaryQueryService.findAll(stockDiaryQuery, pageRequest);
    }
    public void update(StockDiaryId stockDiaryId, StockDiary stockDiary) { stockDiaryCommandService.update(stockDiaryId, stockDiary);}
    public StockDiary findById(StockDiaryId stockDiaryId) {return stockDiaryQueryService.findById(stockDiaryId);}

    public void delete(StockDiaryId stockDiaryId) { stockDiaryCommandService.delete(stockDiaryId);
    }
}

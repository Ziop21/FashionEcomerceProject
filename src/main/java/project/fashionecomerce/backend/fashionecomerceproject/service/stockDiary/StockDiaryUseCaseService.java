package project.fashionecomerce.backend.fashionecomerceproject.service.stockDiary;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.fashionecomerce.backend.fashionecomerceproject.dto.stockDiary.StockDiary;
@Service
@RequiredArgsConstructor
public class StockDiaryUseCaseService {
//    @NonNull
//    final StockDiaryQueryService stockDiaryQueryService;
    @NonNull
    final StockDiaryCommandService stockDiaryCommandService;
    public void save(StockDiary stockDiary) {stockDiaryCommandService.save(stockDiary);
    }
}

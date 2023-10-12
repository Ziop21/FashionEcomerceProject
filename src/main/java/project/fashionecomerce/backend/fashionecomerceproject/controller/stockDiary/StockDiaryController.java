package project.fashionecomerce.backend.fashionecomerceproject.controller.stockDiary;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import project.fashionecomerce.backend.fashionecomerceproject.controller.stockDiary.models.StockDiaryModelMapper;
import project.fashionecomerce.backend.fashionecomerceproject.controller.stockDiary.models.StockDiaryRequest;
import project.fashionecomerce.backend.fashionecomerceproject.dto.stockDiary.StockDiary;
import project.fashionecomerce.backend.fashionecomerceproject.service.stockDiary.StockDiaryUseCaseService;

@RestController
@RequiredArgsConstructor
public class StockDiaryController implements StockDiaryAPI {
    @NonNull
    final StockDiaryUseCaseService stockDiaryUseCaseService;
    @Override
    public void save(StockDiaryRequest stockDiaryRequest) {
        StockDiary stockDiary = StockDiaryModelMapper.INSTANCE.toDTO(stockDiaryRequest);
        stockDiaryUseCaseService.save(stockDiary);
    }
}

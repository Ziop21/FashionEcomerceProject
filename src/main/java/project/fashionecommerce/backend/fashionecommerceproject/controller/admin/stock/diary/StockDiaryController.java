package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.stock.diary;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.stock.diary.models.StockDiaryResponse;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.stock.diary.models.StockDiaryModelMapper;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.stock.diary.models.StockDiaryRequest;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.diary.StockDiary;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.diary.StockDiaryId;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.stock.diary.StockDiaryUseCaseService;

@RestController
@RequiredArgsConstructor

public class StockDiaryController implements StockDiaryAPI{
    @NonNull
    final StockDiaryModelMapper stockDiaryModelMapper;
    @NonNull
    final StockDiaryUseCaseService stockDiaryUseCaseService;
    @Override
    public void update(String stockDiaryId, StockDiaryRequest stockDiaryRequest) {
        StockDiary stockDiary = stockDiaryModelMapper.toDto(stockDiaryRequest);
        stockDiaryUseCaseService.update(new StockDiaryId(stockDiaryId), stockDiary);
    }
    @Override
    public ResponseEntity<StockDiaryResponse> findById(String stockDiaryId) {
        StockDiary stockDiary = stockDiaryUseCaseService.findById(new StockDiaryId(stockDiaryId));
        StockDiaryResponse stockDiaryResponse = stockDiaryModelMapper.toModel(stockDiary);
        return new ResponseEntity<>(stockDiaryResponse, HttpStatus.OK);
    }
    @Override
    public void delete(String stockDiaryId) {
        stockDiaryUseCaseService.delete(new StockDiaryId(stockDiaryId));
    }

}

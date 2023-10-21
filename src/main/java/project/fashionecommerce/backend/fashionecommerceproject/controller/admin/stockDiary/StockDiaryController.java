package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.stockDiary;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.stockDiary.models.StockDiaryModelMapper;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.stockDiary.models.StockDiaryRequest;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.stockDiary.models.StockDiaryResponse;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stockDiary.StockDiary;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stockDiary.StockDiaryId;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.stockDiary.StockDiaryUseCaseService;

@RestController
@RequiredArgsConstructor
public class StockDiaryController {
    @NonNull
    final StockDiaryModelMapper stockDiaryModelMapper;
    @NonNull
    final StockDiaryUseCaseService stockDiaryUseCaseService;
    public void update(String stockDiaryId, StockDiaryRequest stockDiaryRequest) {
        StockDiary stockDiary = stockDiaryModelMapper.toDto(stockDiaryRequest);
        stockDiaryUseCaseService.update(new StockDiaryId(stockDiaryId), stockDiary);
    }

    public ResponseEntity<StockDiaryResponse> findById(String stockDiaryId) {
        StockDiary stockDiary = stockDiaryUseCaseService.findById(new StockDiaryId(stockDiaryId));
        StockDiaryResponse stockDiaryResponse = stockDiaryModelMapper.toModel(stockDiary);
        return new ResponseEntity<>(stockDiaryResponse, HttpStatus.OK);
    }

    public void delete(String stockDiaryId) {
        stockDiaryUseCaseService.delete(new StockDiaryId(stockDiaryId));
    }

}

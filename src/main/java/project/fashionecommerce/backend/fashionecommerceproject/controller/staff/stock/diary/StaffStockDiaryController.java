package project.fashionecommerce.backend.fashionecommerceproject.controller.staff.stock.diary;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import project.fashionecommerce.backend.fashionecommerceproject.controller.staff.stock.diary.models.StaffStockDiaryModelMapper;
import project.fashionecommerce.backend.fashionecommerceproject.controller.staff.stock.diary.models.StaffStockDiaryRequest;
import project.fashionecommerce.backend.fashionecommerceproject.controller.staff.stock.diary.models.StaffStockDiaryResponse;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.diary.StockDiary;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.diary.StockDiaryId;
import project.fashionecommerce.backend.fashionecommerceproject.service.staff.StaffStockDiaryUseCaseService;

@RestController
@RequiredArgsConstructor
public class StaffStockDiaryController implements StaffStockDiaryAPI {
    @NonNull
    final StaffStockDiaryModelMapper staffStockDiaryModelMapper;
    @NonNull
    final StaffStockDiaryUseCaseService staffStockDiaryUseCaseService;
    @Override
    public void update(String stockDiaryId, StaffStockDiaryRequest staffStockDiaryRequest) {
        StockDiary stockDiary = staffStockDiaryModelMapper.toDto(staffStockDiaryRequest);
        staffStockDiaryUseCaseService.update(new StockDiaryId(stockDiaryId), stockDiary);
    }
    @Override
    public ResponseEntity<StaffStockDiaryResponse> findById(String stockDiaryId) {
        StockDiary stockDiary = staffStockDiaryUseCaseService.findById(new StockDiaryId(stockDiaryId));
        StaffStockDiaryResponse finalStock = staffStockDiaryModelMapper.toModel(stockDiary);
        return new ResponseEntity<>(finalStock, HttpStatus.OK);
    }
    @Override
    public void delete(String stockDiaryId) {
        staffStockDiaryUseCaseService.delete(new StockDiaryId(stockDiaryId));
    }
}

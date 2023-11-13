package project.fashionecommerce.backend.fashionecommerceproject.controller.staff.stock.diary;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import project.fashionecommerce.backend.fashionecommerceproject.common.models.PageResponse;
import project.fashionecommerce.backend.fashionecommerceproject.controller.staff.stock.diary.models.StaffStockDiaryModelMapper;
import project.fashionecommerce.backend.fashionecommerceproject.controller.staff.stock.diary.models.StaffStockDiaryRequest;
import project.fashionecommerce.backend.fashionecommerceproject.controller.staff.stock.diary.models.StaffStockDiaryResponse;
import project.fashionecommerce.backend.fashionecommerceproject.dto.handler.MySortHandler;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.diary.StockDiary;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.diary.StockDiaryQuery;
import project.fashionecommerce.backend.fashionecommerceproject.service.staff.StaffStockDiaryUseCaseService;
import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class StaffStockDiariesController implements StaffStockDiariesAPI {
    @NonNull
    final StaffStockDiaryModelMapper staffStockDiaryModelMapper;
    @NonNull
    final StaffStockDiaryUseCaseService staffStockDiaryUseCaseService;
    @Override
    public void save(StaffStockDiaryRequest staffStockDiaryRequest) {
        StockDiary stockDiary = staffStockDiaryModelMapper.toDto(staffStockDiaryRequest);
        staffStockDiaryUseCaseService.save(stockDiary);
    }
    @Override
    public ResponseEntity<PageResponse<StaffStockDiaryResponse>> findAll(String search, LocalDate fromDate, LocalDate toDate,
                                                                         String sort, Integer pageCurrent, Integer pageSize) {
        StockDiaryQuery stockDiaryQuery = StockDiaryQuery.builder()
                .search(search)
                .fromDate(fromDate)
                .toDate(toDate)
                .build();

        PageRequest pageRequest = PageRequest.of(pageCurrent - 1, pageSize, MySortHandler.of(sort));
        Page<StockDiary> stockDiaryPage = staffStockDiaryUseCaseService.findAll(stockDiaryQuery, pageRequest);

        PageResponse<StaffStockDiaryResponse> finalStocks = new PageResponse<>(stockDiaryPage.map(staffStockDiaryModelMapper::toModel));
        return new ResponseEntity<>(finalStocks, HttpStatus.OK);
    }
}

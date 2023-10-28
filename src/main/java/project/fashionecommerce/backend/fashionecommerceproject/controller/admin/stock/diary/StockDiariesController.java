package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.stock.diary;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import project.fashionecommerce.backend.fashionecommerceproject.common.models.PageResponse;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.stock.diary.models.StockDiaryModelMapper;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.stock.diary.models.StockDiaryRequest;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.stock.diary.models.StockDiaryResponse;
import project.fashionecommerce.backend.fashionecommerceproject.dto.handler.MySortHandler;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.diary.StockDiary;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.diary.StockDiaryQuery;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.stock.diary.StockDiaryUseCaseService;
import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class StockDiariesController implements StockDiariesAPI {
    @NonNull
    final StockDiaryModelMapper stockDiaryModelMapper;
    @NonNull
    final StockDiaryUseCaseService stockDiaryUseCaseService;
    @Override
    public void save(StockDiaryRequest stockDiaryRequest) {
        StockDiary stockDiary = stockDiaryModelMapper.toDto(stockDiaryRequest);
        stockDiaryUseCaseService.save(stockDiary);
    }
    @Override
    public ResponseEntity<PageResponse<StockDiaryResponse>> findAll(String search, LocalDate fromDate, LocalDate toDate,
                                                                    String sort, Integer pageCurrent, Integer pageSize) {
        StockDiaryQuery stockDiaryQuery = StockDiaryQuery.builder()
                .search(search)
                .fromDate(fromDate)
                .toDate(toDate)
                .build();

        PageRequest pageRequest = PageRequest.of(pageCurrent - 1, pageSize, MySortHandler.of(sort));
        Page<StockDiary> stockDiaryPage = stockDiaryUseCaseService.findAll(stockDiaryQuery, pageRequest);

        PageResponse<StockDiaryResponse> stockDiaryResponsePageResponse = new PageResponse<>(stockDiaryPage.map(stockDiaryModelMapper::toModel));
        return new ResponseEntity<>(stockDiaryResponsePageResponse, HttpStatus.OK);
    }
}

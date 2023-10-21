package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.stockDiary;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import project.fashionecommerce.backend.fashionecommerceproject.common.models.PageResponse;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.stockDiary.models.StockDiaryModelMapper;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.stockDiary.models.StockDiaryRequest;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.stockDiary.models.StockDiaryResponse;
import project.fashionecommerce.backend.fashionecommerceproject.dto.handler.MySortHandler;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stockDiary.StockDiary;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stockDiary.StockDiaryQuery;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.stockDiary.StockDiaryUseCaseService;

@RestController
@RequiredArgsConstructor
public class StockDiarysController {
    @NonNull
    final StockDiaryModelMapper stockDiaryModelMapper;
    @NonNull
    final StockDiaryUseCaseService stockDiaryUseCaseService;

    public void save(StockDiaryRequest stockDiaryRequest) {
        StockDiary stockDiary = stockDiaryModelMapper.toDto(stockDiaryRequest);
        stockDiaryUseCaseService.save(stockDiary);
    }

    public ResponseEntity<PageResponse<StockDiaryResponse>> findAll(String searchString, String sort, Integer pageCurrent, Integer pageSize) {
        StockDiaryQuery stockDiaryQuery = new StockDiaryQuery(searchString);
        PageRequest pageRequest = PageRequest.of(pageCurrent - 1, pageSize, MySortHandler.of(sort));

        Page<StockDiary> stockDiaryPage = stockDiaryUseCaseService.findAll(stockDiaryQuery, pageRequest);

        PageResponse<StockDiaryResponse> stockDiaryResponsePageResponse = new PageResponse<>(stockDiaryPage.map(stockDiaryModelMapper::toModel));
        return new ResponseEntity<>(stockDiaryResponsePageResponse, HttpStatus.OK);
    }
}

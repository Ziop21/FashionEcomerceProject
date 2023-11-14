package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.stock;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import project.fashionecommerce.backend.fashionecommerceproject.common.models.PageResponse;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.stock.models.StockModelMapper;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.stock.models.StockRequest;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.stock.models.StockResponse;
import project.fashionecommerce.backend.fashionecommerceproject.dto.handler.MySortHandler;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.Stock;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.StockQuery;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.stock.StockUseCaseService;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class StocksController implements StocksAPI {
    @NonNull final StockUseCaseService stockUseCaseService;
    @NonNull final StockModelMapper stockModelMapper;
    @Override
    public void save(StockRequest stockRequest) {
        Stock stock = stockModelMapper.toDto(stockRequest);
        stockUseCaseService.save(stock);
    }

    @Override
    public ResponseEntity<PageResponse<StockResponse>> findAll(String search, List<String> sizeIds, List<String> colorIds, String sort, Integer pageCurrent, Integer pageSize) {
        StockQuery stockQuery = StockQuery.builder()
                .search(search)
                .sizeIds(sizeIds)
                .colorIds(colorIds)
                .build();

        PageRequest pageRequest = PageRequest.of(pageCurrent - 1, pageSize, MySortHandler.of(sort));

        Page<Stock> stockPage = stockUseCaseService.findAll(stockQuery, pageRequest);

        PageResponse<StockResponse> stockResponsePage = new PageResponse<>(stockPage.map(stockModelMapper::toModel));
        return new ResponseEntity<>(stockResponsePage, HttpStatus.OK);
    }
}

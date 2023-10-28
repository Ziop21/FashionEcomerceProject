package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.stock;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.stock.models.StockModelMapper;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.stock.models.StockRequest;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.stock.models.StockResponse;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.Stock;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.StockId;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.stock.StockUseCaseService;

@RestController
@RequiredArgsConstructor
public class StockController implements StockAPI {
    @NonNull final StockUseCaseService stockUseCaseService;
    @NonNull final StockModelMapper stockModelMapper;
    @Override
    public ResponseEntity<StockResponse> findById(String stockId) {
        Stock stock = stockUseCaseService.findById(new StockId(stockId));
        StockResponse stockResponse = stockModelMapper.toModel(stock);
        return new ResponseEntity<>(stockResponse, HttpStatus.OK);
    }

    @Override
    public void delete(String stockId) {
        stockUseCaseService.delete(new StockId(stockId));
    }
    @Override
    public void update(String stockId, StockRequest stockRequest) {
        Stock stock = stockModelMapper.toDto(stockRequest);
        stockUseCaseService.update(new StockId(stockId), stock);
    }

}

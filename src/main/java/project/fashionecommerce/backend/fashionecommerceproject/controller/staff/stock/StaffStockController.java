package project.fashionecommerce.backend.fashionecommerceproject.controller.staff.stock;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import project.fashionecommerce.backend.fashionecommerceproject.controller.staff.stock.models.StaffStockModelMapper;
import project.fashionecommerce.backend.fashionecommerceproject.controller.staff.stock.models.StaffStockRequest;
import project.fashionecommerce.backend.fashionecommerceproject.controller.staff.stock.models.StaffStockResponse;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.Stock;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.StockId;
import project.fashionecommerce.backend.fashionecommerceproject.service.staff.StaffStockUseCaseService;

@RestController
@RequiredArgsConstructor
public class StaffStockController implements StaffStockAPI{
    @NonNull
    final StaffStockUseCaseService staffStockUseCaseService;
    @NonNull final StaffStockModelMapper staffStockModelMapper;
    @Override
    public ResponseEntity<StaffStockResponse> findById(String stockId) {
        Stock stock = staffStockUseCaseService.findById(new StockId(stockId));
        StaffStockResponse staffStockResponse = staffStockModelMapper.toModel(stock);
        return new ResponseEntity<>(staffStockResponse, HttpStatus.OK);
    }

    @Override
    public void delete(String stockId) {
        staffStockUseCaseService.delete(new StockId(stockId));
    }
    @Override
    public void update(String stockId, StaffStockRequest staffStockRequest) {
        Stock stock = staffStockModelMapper.toDto(staffStockRequest);
        staffStockUseCaseService.update(new StockId(stockId), stock);
    }
}

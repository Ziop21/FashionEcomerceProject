package project.fashionecommerce.backend.fashionecommerceproject.controller.staff.stock;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import project.fashionecommerce.backend.fashionecommerceproject.common.models.PageResponse;
import project.fashionecommerce.backend.fashionecommerceproject.controller.staff.stock.models.StaffStockModelMapper;
import project.fashionecommerce.backend.fashionecommerceproject.controller.staff.stock.models.StaffStockRequest;
import project.fashionecommerce.backend.fashionecommerceproject.controller.staff.stock.models.StaffStockResponse;
import project.fashionecommerce.backend.fashionecommerceproject.dto.handler.MySortHandler;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.Stock;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.StockQuery;
import project.fashionecommerce.backend.fashionecommerceproject.service.staff.StaffStockUseCaseService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StaffStocksController implements StaffStocksAPI{
    @NonNull final StaffStockUseCaseService staffStockUseCaseService;
    @NonNull final StaffStockModelMapper staffStockModelMapper;
    @Override
    public void save(StaffStockRequest staffStockRequest) {
        Stock stock = staffStockModelMapper.toDto(staffStockRequest);
        staffStockUseCaseService.save(stock);
    }

    @Override
    public ResponseEntity<PageResponse<StaffStockResponse>> findAll(String search, List<String> sizeIds, List<String> colorIds,
                                                                    String sort, Integer currentPage, Integer pageSize) {
        StockQuery stockQuery = StockQuery.builder()
                .search(search)
                .sizeIds(sizeIds)
                .colorIds(colorIds)
                .build();

        PageRequest pageRequest = PageRequest.of(currentPage - 1, pageSize, MySortHandler.of(sort));

        Page<Stock> stockPage = staffStockUseCaseService.findAll(stockQuery, pageRequest);

        PageResponse<StaffStockResponse> finalStocks = new PageResponse<>(stockPage.map(staffStockModelMapper::toModel));
        return new ResponseEntity<>(finalStocks, HttpStatus.OK);
    }
}

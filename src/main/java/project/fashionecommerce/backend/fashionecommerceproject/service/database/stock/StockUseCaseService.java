package project.fashionecommerce.backend.fashionecommerceproject.service.database.stock;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.fashionecommerce.backend.fashionecommerceproject.dto.enums.ERole;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.Stock;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.StockId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.StockQuery;

@Service
@RequiredArgsConstructor
public class StockUseCaseService {
    @NonNull final StockCommandService stockCommandService;
    @NonNull final StockQueryService stockQueryService;
    @Transactional
    public void update(StockId stockId, Stock stock) {
        stockCommandService.update(stockId, stock);
    }
    @Transactional
    public void delete(StockId stockId) {
        stockCommandService.delete(stockId);
    }
    @Transactional
    public void save(Stock stock) {
        stockCommandService.save(stock);
    }

    public Page<Stock> findAll(StockQuery stockQuery, PageRequest pageRequest) {
        return stockQueryService.findAll(stockQuery, pageRequest, ERole.ADMIN);
    }

    public Stock findById(StockId stockId) {
        return stockQueryService.findById(stockId);
    }
}

package project.fashionecommerce.backend.fashionecommerceproject.service.staff;

import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import project.fashionecommerce.backend.fashionecommerceproject.config.security.userDetails.Impl.UserDetailsImpl;
import project.fashionecommerce.backend.fashionecommerceproject.dto.enums.ERole;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.Stock;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.StockId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.StockMapper;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.StockQuery;
import project.fashionecommerce.backend.fashionecommerceproject.exception.MyForbiddenException;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.stock.StockCommandService;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.stock.StockQueryService;

@Service
@RequiredArgsConstructor
public class StaffStockUseCaseService {
    @NonNull final StockCommandService stockCommandService;
    @NonNull final StockQueryService stockQueryService;
    @NonNull final StockMapper stockMapper;
    @Transactional
    public void update(StockId stockId, Stock newStock) {
        Stock foundStock = stockQueryService.findById(stockId);
        if (foundStock.isDeleted() || foundStock.isActive()) {
            throw new MyForbiddenException();
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        if (!userDetails.getId().equals(foundStock.createdBy())){
            throw new MyForbiddenException();
        }
        foundStock = stockMapper.updateDto(foundStock, newStock.productId(), newStock.colorId(),
                newStock.sizeId(), newStock.quantity());
        stockCommandService.update(stockId, foundStock);
    }
    @Transactional
    public void delete(StockId stockId) {
        Stock stock = stockQueryService.findById(stockId);
        if (stock.isDeleted() || stock.isActive()) {
            throw new MyForbiddenException();
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        if (!userDetails.getId().equals(stock.createdBy())){
            throw new MyForbiddenException();
        }
        stock = stockMapper.updateDtoIsDeleted(stock, true);
        stockCommandService.update(stockId, stock);
    }
    @Transactional
    public void save(Stock stock) {
        stock = stockMapper.updateDtoIsActive(stock, false);
        stock = stockMapper.updateDtoIsDeleted(stock, false);
        stockCommandService.save(stock);
    }

    public Page<Stock> findAll(StockQuery stockQuery, PageRequest pageRequest) {
        Page<Stock> stockPage = stockQueryService.findAll(stockQuery, pageRequest, ERole.STAFF);
        return stockPage;
    }

    public Stock findById(StockId stockId) {
        Stock stock = stockQueryService.findById(stockId);
        if (stock.isDeleted() || stock.isActive()) {
            throw new MyForbiddenException();
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        if (!userDetails.getId().equals(stock.createdBy())){
            throw new MyForbiddenException();
        }
        return stock;
    }
}

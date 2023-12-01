package project.fashionecommerce.backend.fashionecommerceproject.service.database.stock;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.fashionecommerce.backend.fashionecommerceproject.dto.color.ColorId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.enums.ERole;
import project.fashionecommerce.backend.fashionecommerceproject.dto.product.ProductId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.size.SizeId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.Stock;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.StockId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.StockMapper;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.StockQuery;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.color.ColorQueryService;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.product.ProductQueryService;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.size.SizeQueryService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StockUseCaseService {
    @NonNull final StockCommandService stockCommandService;
    @NonNull final StockQueryService stockQueryService;
    @NonNull final ProductQueryService productQueryService;
    @NonNull final SizeQueryService sizeQueryService;
    @NonNull final ColorQueryService colorQueryService;
    @NonNull final StockMapper stockMapper;
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
        Page<Stock> stockPage = stockQueryService.findAll(stockQuery, pageRequest, ERole.ADMIN);
        stockPage = stockPage.map(stock -> {
            String productName = productQueryService.findById(new ProductId(stock.productId())).name();
            return stockMapper.updateDtoProductName(stock, productName);
        });
        stockPage = stockPage.map(stock -> {
            String sizeName = sizeQueryService.findById(new SizeId(stock.sizeId())).name();
            return stockMapper.updateDtoSizeName(stock, sizeName);
        });
        stockPage = stockPage.map(stock -> {
            String colorName = colorQueryService.findById(new ColorId(stock.colorId())).name();
            return stockMapper.updateDtoColorName(stock, colorName);
        });
        return stockPage;
    }

    public Stock findById(StockId stockId) {
        return stockQueryService.findById(stockId);
    }
}

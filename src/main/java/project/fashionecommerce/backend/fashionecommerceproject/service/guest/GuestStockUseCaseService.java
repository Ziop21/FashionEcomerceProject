package project.fashionecommerce.backend.fashionecommerceproject.service.guest;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import project.fashionecommerce.backend.fashionecommerceproject.dto.color.ColorId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.enums.ERole;
import project.fashionecommerce.backend.fashionecommerceproject.dto.product.ProductId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.size.SizeId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.Stock;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.StockId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.StockQuery;
import project.fashionecommerce.backend.fashionecommerceproject.exception.MyResourceNotFoundException;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.stock.StockQueryService;

@Service
@RequiredArgsConstructor
public class GuestStockUseCaseService {
    @NonNull
    final StockQueryService stockQueryService;
    public Stock findById(StockId stockId){
        Stock stock = stockQueryService.findById(stockId);
        if (stock.isDeleted() || !stock.isActive())
            throw new MyResourceNotFoundException();
        return stock;
    }
    public Page<Stock> findAllByProductId(StockQuery query, PageRequest pageRequest) {
        Page<Stock> stocks = stockQueryService.findAll(query, pageRequest, ERole.GUEST);
        return stocks;
    }

    public Stock findByProductIdSizeIdColorId(ProductId productId, SizeId sizeId, ColorId colorId) {
        Stock stock = stockQueryService.findByProductIdSizeIdColorId(productId, sizeId, colorId);
        if (!stock.isActive() || stock.isDeleted())
            throw new MyResourceNotFoundException();
        return stock;
    }
}

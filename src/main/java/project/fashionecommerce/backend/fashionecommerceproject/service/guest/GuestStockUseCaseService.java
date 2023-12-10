package project.fashionecommerce.backend.fashionecommerceproject.service.guest;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import project.fashionecommerce.backend.fashionecommerceproject.dto.enums.ERole;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.Stock;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.StockQuery;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.stock.StockQueryService;

@Service
@RequiredArgsConstructor
public class GuestStockUseCaseService {
    @NonNull
    final StockQueryService stockQueryService;
    public Page<Stock> findAllByProductId(StockQuery query, PageRequest pageRequest) {
        Page<Stock> stocks = stockQueryService.findAll(query, pageRequest, ERole.GUEST);
        return stocks;
    }
}

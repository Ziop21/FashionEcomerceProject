package project.fashionecommerce.backend.fashionecommerceproject.controller.guest.stock;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import project.fashionecommerce.backend.fashionecommerceproject.common.models.PageResponse;
import project.fashionecommerce.backend.fashionecommerceproject.controller.guest.stock.models.GuestStockModelMapper;
import project.fashionecommerce.backend.fashionecommerceproject.controller.guest.stock.models.GuestStockResponse;
import project.fashionecommerce.backend.fashionecommerceproject.dto.color.ColorId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.handler.MySortHandler;
import project.fashionecommerce.backend.fashionecommerceproject.dto.product.ProductId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.size.SizeId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.Stock;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.StockQuery;
import project.fashionecommerce.backend.fashionecommerceproject.service.guest.GuestStockUseCaseService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GuestStockController implements GuestStockAPI {
    @NonNull final GuestStockUseCaseService guestStockUseCaseService;
    @NonNull final GuestStockModelMapper guestStockModelMapper;
    @Override
    public ResponseEntity<PageResponse<GuestStockResponse>> findAlByProductId(String productId, String search,
    List<String> colorIds, List<String> sizeIds, String sort, Integer currentPage, Integer pageSize) {
        StockQuery query = StockQuery.builder()
                .productId(productId)
                .search(search)
                .colorIds(colorIds)
                .sizeIds(sizeIds)
                .build();
        PageRequest pageRequest = PageRequest.of(currentPage - 1, pageSize, MySortHandler.of(sort));

        Page<Stock> stocks = guestStockUseCaseService.findAllByProductId(query, pageRequest);

        PageResponse<GuestStockResponse> finalStocks = new PageResponse<>(stocks.map(guestStockModelMapper::toModel));
        return new ResponseEntity<>(finalStocks, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<GuestStockResponse> findByProductIdSizeIdColorId(String productId,
                                                                           String colorId, String sizeId) {
        Stock stock = guestStockUseCaseService.findByProductIdSizeIdColorId(
                new ProductId(productId),
                new SizeId(sizeId),
                new ColorId(colorId)
        );
        return new ResponseEntity<>(guestStockModelMapper.toModel(stock), HttpStatus.OK);
    }
}

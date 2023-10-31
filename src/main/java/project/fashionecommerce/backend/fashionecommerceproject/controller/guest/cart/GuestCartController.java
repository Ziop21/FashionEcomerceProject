package project.fashionecommerce.backend.fashionecommerceproject.controller.guest.cart;

import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.StockId;
import project.fashionecommerce.backend.fashionecommerceproject.service.guest.GuestCartUseCaseService;

@RestController
@RequiredArgsConstructor
public class GuestCartController implements GuestCartAPI {
    @NonNull
    final GuestCartUseCaseService guestCartUseCaseService;

    @Override
    public void deleteCartItem(String stockId, HttpServletRequest request) {
        guestCartUseCaseService.deleteCartItem(new StockId(stockId), request);
    }

    @Override
    public void insertToCart(String stockId, Long quantity, HttpServletRequest request) {
        guestCartUseCaseService.insertToCart(stockId, quantity, request);
    }

    @Override
    public void updateQuantity(String stockId, Long quantity, HttpServletRequest request) {
        guestCartUseCaseService.updateQuantity(stockId, quantity, request);
    }

}

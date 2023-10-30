package project.fashionecommerce.backend.fashionecommerceproject.controller.guest.cart;

import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import project.fashionecommerce.backend.fashionecommerceproject.service.guest.GuestCartUseCaseService;

@RestController
@RequiredArgsConstructor
public class GuestCartController implements GuestCartAPI {
    @NonNull
    final GuestCartUseCaseService guestCartUseCaseService;

    @Override
    public ResponseEntity<?> insertToCart(String stockId, HttpServletRequest request) {
        guestCartUseCaseService.insertToCart(stockId, request);
        return ResponseEntity
                .ok()
                .body("Product have been inserted to your cart");
    }
}

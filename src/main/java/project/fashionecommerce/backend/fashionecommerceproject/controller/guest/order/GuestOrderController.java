package project.fashionecommerce.backend.fashionecommerceproject.controller.guest.order;

import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import project.fashionecommerce.backend.fashionecommerceproject.controller.guest.order.models.GuestOrderModelMapper;
import project.fashionecommerce.backend.fashionecommerceproject.controller.guest.order.models.GuestOrderRequest;
import project.fashionecommerce.backend.fashionecommerceproject.dto.order.Order;
import project.fashionecommerce.backend.fashionecommerceproject.service.guest.GuestOrderUseCaseService;

@RestController
@RequiredArgsConstructor
public class GuestOrderController implements GuestOrderAPI {
    @NonNull final GuestOrderUseCaseService guestOrderUseCaseService;
    @NonNull final GuestOrderModelMapper guestOrderModelMapper;
    @Override
    public ResponseEntity<?> save(GuestOrderRequest guestOrderRequest) {
        Order order = guestOrderModelMapper.toDto(guestOrderRequest);
        guestOrderUseCaseService.save(order, guestOrderRequest.cartToken());
        return ResponseEntity.ok()
                .body("Success");
    }
}

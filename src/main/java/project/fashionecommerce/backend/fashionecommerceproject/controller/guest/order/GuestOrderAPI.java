package project.fashionecommerce.backend.fashionecommerceproject.controller.guest.order;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import project.fashionecommerce.backend.fashionecommerceproject.controller.guest.order.models.GuestOrderRequest;

@RequestMapping("/api/guest/order")
public interface GuestOrderAPI {
    @PostMapping
    void save(@RequestBody @Valid GuestOrderRequest guestOrderRequest, HttpServletRequest request);
}

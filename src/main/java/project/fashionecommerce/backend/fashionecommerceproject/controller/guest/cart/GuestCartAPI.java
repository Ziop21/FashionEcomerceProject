package project.fashionecommerce.backend.fashionecommerceproject.controller.guest.cart;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/guest/cart")
public interface GuestCartAPI {
    @PostMapping("/{stockId}")
    ResponseEntity<?> insertToCart(@PathVariable String stockId, HttpServletRequest request);
}

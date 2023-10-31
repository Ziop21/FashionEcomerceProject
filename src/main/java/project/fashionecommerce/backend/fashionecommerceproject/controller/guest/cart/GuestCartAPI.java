package project.fashionecommerce.backend.fashionecommerceproject.controller.guest.cart;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/api/guest/cart")
public interface GuestCartAPI {
    @DeleteMapping("/delete/{stockId}")
    void deleteCartItem(@PathVariable String stockId,
                        HttpServletRequest request);

    @PostMapping("/add/{stockId}")
    void insertToCart(@PathVariable String stockId,
                      @RequestParam(required = false, value = "quantity", defaultValue = "1") Long quantity,
                      HttpServletRequest request);
    @PostMapping("/update/{stockId}")
    void updateQuantity(@PathVariable String stockId,
                        @RequestParam(required = false, value = "quantity", defaultValue = "1") Long quantity,
                        HttpServletRequest request);
}

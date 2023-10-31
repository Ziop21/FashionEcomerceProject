package project.fashionecommerce.backend.fashionecommerceproject.controller.guest.cart;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.fashionecommerce.backend.fashionecommerceproject.controller.guest.cart.items.models.GuestCartItemResponse;
import java.util.List;

@RequestMapping("/api/guest/cart")
public interface GuestCartAPI {
    @GetMapping
    ResponseEntity<List<GuestCartItemResponse>> findAllCartItem(HttpServletRequest request);

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

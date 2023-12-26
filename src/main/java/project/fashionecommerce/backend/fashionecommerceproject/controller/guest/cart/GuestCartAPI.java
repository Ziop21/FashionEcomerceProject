package project.fashionecommerce.backend.fashionecommerceproject.controller.guest.cart;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.fashionecommerce.backend.fashionecommerceproject.controller.guest.cart.items.models.GuestCartItemRequest;
import project.fashionecommerce.backend.fashionecommerceproject.controller.guest.cart.items.models.GuestCartItemResponse;
import project.fashionecommerce.backend.fashionecommerceproject.controller.guest.cart.models.GuestCartRequest;

import java.util.List;

@RequestMapping("/api/guest/cart")
public interface GuestCartAPI {
    @PostMapping
    ResponseEntity<?> save();

    @GetMapping
    ResponseEntity<List<GuestCartItemResponse>> findAllCartItem(@RequestBody @Valid GuestCartRequest guestCartRequest);

    @DeleteMapping("/delete/{stockId}")
    void deleteCartItem(@PathVariable String stockId,
                        @RequestBody @Valid GuestCartRequest guestCartRequest);
    @PostMapping("/addCartItems")
    void addCartItems(@RequestBody @Valid GuestCartRequest guestCartRequest);
    @PostMapping("/add/{stockId}")
    void insertToCart(@PathVariable String stockId,
                      @RequestParam(value = "quantity", defaultValue = "1") Long quantity,
                      @RequestBody @Valid GuestCartRequest guestCartRequest);
    @PostMapping("/update/{stockId}")
    void updateQuantity(@PathVariable String stockId,
                        @RequestParam(value = "quantity", defaultValue = "1") Long quantity,
                        @RequestBody @Valid GuestCartRequest guestCartRequest);
}

package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.cart;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.cart.models.CartRequest;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.cart.models.CartResponse;

@RequestMapping("/api/admin/cart/{cartId}")
public interface CartAPI {
    @GetMapping
    ResponseEntity<CartResponse> findById(@PathVariable String cartId);

    @DeleteMapping
    void delete(@PathVariable String cartId);

    @PutMapping
    void update(@PathVariable String cartId, @RequestBody @Valid CartRequest cartRequest);
}

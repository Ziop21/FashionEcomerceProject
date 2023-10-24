package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.cart;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.cart.models.CartModelMapper;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.cart.models.CartRequest;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.cart.models.CartResponse;
import project.fashionecommerce.backend.fashionecommerceproject.dto.cart.Cart;
import project.fashionecommerce.backend.fashionecommerceproject.dto.cart.CartId;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.cart.CartUseCaseService;

@RestController
@RequiredArgsConstructor
public class CartController implements CartAPI {
    @NonNull final CartUseCaseService cartUseCaseService;
    @NonNull final CartModelMapper cartModelMapper;

    @Override
    public ResponseEntity<CartResponse> findById(String cartId) {
        Cart cart = cartUseCaseService.findById(new CartId(cartId));
        return new ResponseEntity<>(cartModelMapper.toModel(cart), HttpStatus.OK);
    }

    @Override
    public void delete(String cartId) {
        cartUseCaseService.delete(new CartId(cartId));
    }

    @Override
    public void update(String cartId, CartRequest cartRequest) {
        Cart cart = cartModelMapper.toDto(cartRequest);
        cartUseCaseService.update(new CartId(cartId), cart);
    }
}

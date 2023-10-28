package project.fashionecommerce.backend.fashionecommerceproject.service.database.cart;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.fashionecommerce.backend.fashionecommerceproject.dto.cart.Cart;
import project.fashionecommerce.backend.fashionecommerceproject.dto.cart.CartId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.cart.CartQuery;

@Service
@RequiredArgsConstructor
public class CartUseCaseService {
    @NonNull final CartCommandService cartCommandService;
    @NonNull final CartQueryService cartQueryService;
    @Transactional
    public void update(CartId cartId, Cart cart) {
        cartCommandService.update(cartId, cart);
    }
    @Transactional
    public void delete(CartId cartId) {
        cartCommandService.delete(cartId);
    }
    @Transactional
    public void save(Cart cart) {
        cartCommandService.save(cart);
    }

    public Page<Cart> findAll(CartQuery cartQuery, PageRequest pageRequest) {
        return cartQueryService.findAll(cartQuery, pageRequest);
    }

    public Cart findById(CartId cartId) {
        return cartQueryService.findById(cartId);
    }
}

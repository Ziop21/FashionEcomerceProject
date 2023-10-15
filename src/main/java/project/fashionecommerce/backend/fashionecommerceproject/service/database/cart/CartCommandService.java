package project.fashionecommerce.backend.fashionecommerceproject.service.database.cart;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.fashionecommerce.backend.fashionecommerceproject.dto.cart.Cart;
import project.fashionecommerce.backend.fashionecommerceproject.dto.cart.CartMapper;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.cart.CartEntity;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.cart.CartRepository;

@Service
@RequiredArgsConstructor
public class CartCommandService {
    @NonNull final CartRepository cartRepository;
    @NonNull final CartMapper cartMapper;
    public Cart save(Cart cart) {
        CartEntity cartEntity = cartMapper.toEntity(cart);
        cartRepository.save(cartEntity);
        return cartMapper.toDto(cartEntity);
    }
}

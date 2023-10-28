package project.fashionecommerce.backend.fashionecommerceproject.service.database.cart;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.fashionecommerce.backend.fashionecommerceproject.dto.cart.Cart;
import project.fashionecommerce.backend.fashionecommerceproject.dto.cart.CartId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.cart.CartMapper;
import project.fashionecommerce.backend.fashionecommerceproject.exception.MyConflictsException;
import project.fashionecommerce.backend.fashionecommerceproject.exception.MyResourceNotFoundException;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.cart.CartEntity;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.cart.CartRepository;

@Service
@RequiredArgsConstructor
public class CartCommandService {
    @NonNull final CartRepository cartRepository;
    @NonNull final CartMapper cartMapper;
    public Cart save(Cart cart) {
        CartEntity cartEntity = cartMapper.toEntity(cart);
        if (cart.userId() != null && cart.isDeleted().equals(false) && cartRepository.existsByUserIdAndIsDeleted(cart.userId(), false))
            throw new MyConflictsException();
        cartRepository.save(cartEntity);
        return cartMapper.toDto(cartEntity);
    }

    public void delete(CartId cartId) {
        cartRepository.deleteById(cartId.id());
    }

    public void update(CartId cartId, Cart cart) {
        CartEntity foundEntity = cartRepository.findById(cartId.id()).orElseThrow(MyResourceNotFoundException::new);
        if (!cart.userId().equals(foundEntity.getUserId())
        && cartRepository.existsByUserIdAndIsDeleted(cart.userId(), false))
            throw new MyConflictsException();
        cartMapper.updateExist(cart, foundEntity);
        cartRepository.save(foundEntity);
    }
}
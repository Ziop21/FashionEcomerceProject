package project.fashionecommerce.backend.fashionecommerceproject.service.guest;

import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.fashionecommerce.backend.fashionecommerceproject.dto.cart.Cart;
import project.fashionecommerce.backend.fashionecommerceproject.dto.cart.CartId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.cart.CartMapper;
import project.fashionecommerce.backend.fashionecommerceproject.dto.cart.items.CartItem;
import project.fashionecommerce.backend.fashionecommerceproject.dto.order.Order;
import project.fashionecommerce.backend.fashionecommerceproject.dto.order.OrderMapper;
import project.fashionecommerce.backend.fashionecommerceproject.dto.user.User;
import project.fashionecommerce.backend.fashionecommerceproject.exception.MyForbiddenException;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.cart.CartCommandService;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.cart.CartQueryService;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.order.OrderCommandService;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.user.UserQueryService;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GuestOrderUseCaseService {
    @NonNull final HomeUseCaseService homeUseCaseService;

    @NonNull final OrderCommandService orderCommandService;
    @NonNull final CartCommandService cartCommandService;
    @NonNull final GuestCartUseCaseService guestCartUseCaseService;

    @NonNull final CartQueryService cartQueryService;
    @NonNull final UserQueryService userQueryService;

    @NonNull final OrderMapper orderMapper;
    @NonNull final CartMapper cartMapper;

    @Transactional
    public void save(Order order, HttpServletRequest request) {
        String cartId = guestCartUseCaseService.getCartId(request);
        Cart cart = cartQueryService.findById(new CartId(cartId));
        List<CartItem> cartItems = cart.cartItems();
        if (cartItems == null || cartItems.size() == 0){
            throw new MyForbiddenException();
        }
        order = orderMapper.updateDto(order, );

        User user = userQueryService.findByEmail();

        cart = cartMapper.updateDto(cart,false, true);
        cartCommandService.update(new CartId(cartId), cart);
    }
}

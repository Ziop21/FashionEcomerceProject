package project.fashionecommerce.backend.fashionecommerceproject.service.guest;

import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.fashionecommerce.backend.fashionecommerceproject.dto.cart.Cart;
import project.fashionecommerce.backend.fashionecommerceproject.dto.cart.CartId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.cart.CartMapper;
import project.fashionecommerce.backend.fashionecommerceproject.dto.order.Order;
import project.fashionecommerce.backend.fashionecommerceproject.dto.order.OrderMapper;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.cart.CartCommandService;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.cart.CartQueryService;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.order.OrderCommandService;

@Service
@RequiredArgsConstructor
public class GuestOrderUseCaseService {
    @NonNull final OrderCommandService orderCommandService;
    @NonNull final CartCommandService cartCommandService;
    @NonNull final GuestCartUseCaseService guestCartUseCaseService;

    @NonNull final CartQueryService cartQueryService;
    @NonNull final OrderMapper orderMapper;
    @NonNull final CartMapper cartMapper;
    public void save(Order order, HttpServletRequest request) {
        String cartId = guestCartUseCaseService.getCartId(request);
        Cart cart = cartQueryService.findById(new CartId(cartId));
//        cart = cartMapper.updateDto()
//        order = orderMapper.updateDto(order)
    }
}

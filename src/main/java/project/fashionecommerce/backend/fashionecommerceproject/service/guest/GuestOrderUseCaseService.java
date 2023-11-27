package project.fashionecommerce.backend.fashionecommerceproject.service.guest;

import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.fashionecommerce.backend.fashionecommerceproject.config.security.userDetails.Implement.UserDetailsImpl;
import project.fashionecommerce.backend.fashionecommerceproject.dto.cart.Cart;
import project.fashionecommerce.backend.fashionecommerceproject.dto.cart.CartId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.cart.CartMapper;
import project.fashionecommerce.backend.fashionecommerceproject.dto.cart.items.CartItem;
import project.fashionecommerce.backend.fashionecommerceproject.dto.enums.EOrderStatus;
import project.fashionecommerce.backend.fashionecommerceproject.dto.order.Order;
import project.fashionecommerce.backend.fashionecommerceproject.dto.order.OrderMapper;
import project.fashionecommerce.backend.fashionecommerceproject.dto.order.items.OrderItem;
import project.fashionecommerce.backend.fashionecommerceproject.exception.MyResourceNotFoundException;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.cart.CartCommandService;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.cart.CartQueryService;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.order.OrderCommandService;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.user.UserQueryService;
import project.fashionecommerce.backend.fashionecommerceproject.util.JwtUtils;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GuestOrderUseCaseService {
    @NonNull final GuestUseCaseService guestUseCaseService;

    @NonNull final OrderCommandService orderCommandService;
    @NonNull final CartCommandService cartCommandService;
    @NonNull final GuestCartUseCaseService guestCartUseCaseService;

    @NonNull final CartQueryService cartQueryService;
    @NonNull final UserQueryService userQueryService;

    @NonNull final OrderMapper orderMapper;
    @NonNull final CartMapper cartMapper;

    @NonNull final JwtUtils jwtUtils;

    @Value("${fashion_ecommerce.app.jwtCartCookieName}")
    private String cartTokenCookieName;

    @Transactional
    public ResponseCookie save(Order order, HttpServletRequest request) {
        String cartId = guestCartUseCaseService.getCartId(request);
        Cart cart = cartQueryService.findById(new CartId(cartId));
        List<CartItem> cartItems = cart.cartItems();
        if (cartItems == null || cartItems.size() == 0){
            throw new MyResourceNotFoundException();
        }
        List<OrderItem> orderItems = cartItems.stream().map(cartItem -> OrderItem.builder()
                        .stockId(cartItem.stockId())
                        .quantity(cartItem.quantity())
                        .isDeleted(false)
                        .isActive(true)
                        .build())
                .collect(Collectors.toList());
        order = orderMapper.updateDto(order, EOrderStatus.WAITING, orderItems);
        order = orderMapper.updateDtoIsActive(order, true);
        order = orderMapper.updateDtoIsDeleted(order, false);
        if (guestUseCaseService.getCurrentUser() != "anonymousUser"){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            order = orderMapper.updateDtoUserId(order, userDetails.getId());
        }
        orderCommandService.save(order);
        cartCommandService.updateIsActiveIsDeleted(new CartId(cartId), false, true);

        Cart newCart = Cart.builder().isActive(true).isDeleted(false).build();
        newCart = cartCommandService.save(newCart);
        String cartToken = jwtUtils.generateTokenFromCartId(newCart.id());
//        ResponseCookie cartTokenCookie = jwtUtils.generateCookie(cartTokenCookieName, cartToken, "/api");
//        return cartTokenCookie;
        return null;
    }
}

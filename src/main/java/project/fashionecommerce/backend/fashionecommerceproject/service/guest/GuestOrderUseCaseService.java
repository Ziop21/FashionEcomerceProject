package project.fashionecommerce.backend.fashionecommerceproject.service.guest;

import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.fashionecommerce.backend.fashionecommerceproject.config.security.userDetails.Implement.UserDetailsImpl;
import project.fashionecommerce.backend.fashionecommerceproject.dto.cart.Cart;
import project.fashionecommerce.backend.fashionecommerceproject.dto.cart.CartId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.cart.CartMapper;
import project.fashionecommerce.backend.fashionecommerceproject.dto.cart.items.CartItem;
import project.fashionecommerce.backend.fashionecommerceproject.dto.delivery.DeliveryId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.enums.EOrderStatus;
import project.fashionecommerce.backend.fashionecommerceproject.dto.order.Order;
import project.fashionecommerce.backend.fashionecommerceproject.dto.order.OrderMapper;
import project.fashionecommerce.backend.fashionecommerceproject.dto.order.items.OrderItem;
import project.fashionecommerce.backend.fashionecommerceproject.exception.MyResourceNotFoundException;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.cart.CartCommandService;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.cart.CartQueryService;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.delivery.DeliveryQueryService;
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
    @NonNull final DeliveryQueryService deliveryQueryService;

    @NonNull final OrderMapper orderMapper;
    @NonNull final CartMapper cartMapper;

    @NonNull final JwtUtils jwtUtils;
    @Autowired
    private JavaMailSender javaMailSender;

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
        if (guestUseCaseService.getCurrentUser() != "anonymousUser"){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            this.sendEmailOrderInformation(order, userDetails.getEmail());
        }
//        ResponseCookie cartTokenCookie = jwtUtils.generateCookie(cartTokenCookieName, cartToken, "/api");
//        return cartTokenCookie;
        return null;
    }

    private void sendEmailOrderInformation(Order order, String email) {
        String deliveryName = deliveryQueryService.findById(new DeliveryId(order.deliveryId())).name();
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("FASHION ECOMMERCE WEBSITE: ORDER INFORMATION");
        StringBuilder text = new StringBuilder("Thank you for choosing our website!! This is your order information:\n\n");
        text.append("Order ID: ").append(order.id()).append("\n");
        text.append("Address: ").append(order.address()).append("\n");
        text.append("Phone: ").append(order.phone()).append("\n");
        text.append("Items: ").append(deliveryName).append("\n");
        text.append("Delivery: ").append(order.orderItems()).append("\n");
        text.append("Shipping fee: ").append(order.shippingFee()).append("\n");
        text.append("Paid before: ").append(order.isPaidBefore()).append("\n");
        message.setText(text.toString());
        javaMailSender.send(message);
    }

}

package project.fashionecommerce.backend.fashionecommerceproject.service.guest;

import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.fashionecommerce.backend.fashionecommerceproject.dto.cart.Cart;
import project.fashionecommerce.backend.fashionecommerceproject.dto.cart.CartId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.cart.CartMapper;
import project.fashionecommerce.backend.fashionecommerceproject.dto.cart.items.CartItem;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.StockId;
import project.fashionecommerce.backend.fashionecommerceproject.exception.MyForbiddenException;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.cart.CartCommandService;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.cart.CartQueryService;
import project.fashionecommerce.backend.fashionecommerceproject.util.JwtUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GuestCartUseCaseService {
    @NonNull
    final JwtUtils jwtUtils;

    @NonNull final CartCommandService cartCommandService;
    @NonNull final CartQueryService cartQueryService;
    @NonNull final CartMapper cartMapper;

    @Value("${fashion_ecommerce.app.jwtCartCookieName}")
    private String cartTokenCookieName;
    @Transactional
    public void insertToCart(String stockId, Long quantity, HttpServletRequest request){
        String cartId = this.getCartId(request);
        Cart cart = cartQueryService.findById(new CartId(cartId));
        List<CartItem> cartItems = cart.cartItems();
        if (cart.cartItems() == null)
            cartItems = new ArrayList<>();
        CartItem oldCartItem = null;

        if (cartItems.stream().map(CartItem::stockId).collect(Collectors.toList()).contains(stockId)){
            oldCartItem = cartItems.stream().filter(cartItem -> cartItem.stockId().equals(stockId)).findFirst().get();
            cartItems = this.updateQuantity(cartItems, stockId, oldCartItem.quantity() + quantity);
        }
        else{
            CartItem newCartItem = CartItem.builder()
                    .stockId(stockId)
                    .quantity(quantity)
                    .isDeleted(false)
                    .isActive(true)
                    .build();
            cartItems.add(newCartItem);
        }
        cart = cartMapper.updateCartItems(cart, cartItems);
        cartCommandService.save(cart);
    }

    @Transactional
    public void updateQuantity(String stockId, Long quantity, HttpServletRequest request) {
        String cartId = this.getCartId(request);
        Cart cart = cartQueryService.findById(new CartId(cartId));
        List<CartItem> cartItems = cart.cartItems();
        cartItems = this.updateQuantity(cartItems, stockId, quantity);
        cart = cartMapper.updateCartItems(cart, cartItems);
        cartCommandService.save(cart);
    }
    public String getCartId(HttpServletRequest request){
        String cartToken = jwtUtils.getCookieValueByName(request, cartTokenCookieName);
        if (jwtUtils.validateJwtToken(cartToken) == false) {
            throw new MyForbiddenException();
        }
        String cartId = jwtUtils.getValueFromJwtToken(cartToken);
        return cartId;
    }
    public List<CartItem> updateQuantity(List<CartItem> cartItems, String stockId, Long quantity){
        for (int i = 0; i < cartItems.size(); i++){
            if (cartItems.get(i).stockId().equals(stockId)){
                CartItem oldCartItem = cartItems.get(i);
                cartItems.remove(oldCartItem);
                CartItem newCartItem = CartItem.builder()
                        .stockId(oldCartItem.stockId())
                        .quantity(quantity)
                        .isActive(oldCartItem.isActive())
                        .isDeleted(oldCartItem.isDeleted())
                        .createdAt(oldCartItem.createdAt())
                        .updatedAt(oldCartItem.updatedAt())
                        .build();
                cartItems.add(newCartItem);
                break;
            }
        }
        return cartItems;
    }
    @Transactional
    public void deleteCartItem(StockId stockId, HttpServletRequest request) {
        String cartId = this.getCartId(request);
        Cart cart = cartQueryService.findById(new CartId(cartId));
        List<CartItem> cartItems = cart.cartItems();
        for (int i = 0; i < cartItems.size(); i++) {
            if (cartItems.get(i).stockId().equals(stockId.id())) {
                cartItems.remove(i);
                break;
            }
        }
        cart = cartMapper.updateCartItems(cart, cartItems);
        cartCommandService.save(cart);
    }
}

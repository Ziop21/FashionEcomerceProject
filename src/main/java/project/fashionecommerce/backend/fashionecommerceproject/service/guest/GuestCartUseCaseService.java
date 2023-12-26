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
import project.fashionecommerce.backend.fashionecommerceproject.dto.cart.items.CartItemMapper;
import project.fashionecommerce.backend.fashionecommerceproject.dto.color.ColorId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.product.Product;
import project.fashionecommerce.backend.fashionecommerceproject.dto.product.ProductId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.size.SizeId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.Stock;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.StockId;
import project.fashionecommerce.backend.fashionecommerceproject.exception.MyForbiddenException;
import project.fashionecommerce.backend.fashionecommerceproject.exception.MyResourceNotFoundException;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.cart.CartCommandService;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.cart.CartQueryService;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.color.ColorQueryService;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.product.ProductQueryService;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.size.SizeQueryService;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.stock.StockQueryService;
import project.fashionecommerce.backend.fashionecommerceproject.util.JwtUtils;

import java.text.ParseException;
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
    @NonNull final StockQueryService stockQueryService;
    @NonNull final ProductQueryService productQueryService;
    @NonNull final ColorQueryService colorQueryService;
    @NonNull final SizeQueryService sizeQueryService;

    @NonNull final CartMapper cartMapper;
    @NonNull final CartItemMapper cartItemMapper;


    public List<CartItem> findAllCartItem(String cartToken) {
        String cartId = this.getCartId(cartToken);
        Cart cart = cartQueryService.findById(new CartId(cartId));
        if (!cart.isActive())
            throw new MyResourceNotFoundException();
        List<CartItem> cartItems = cart.cartItems();
        if (cart.cartItems() == null) {
            cartItems = new ArrayList<>();
            return cartItems;
        }
        List<String> stockIds = cartItems.stream()
                .map(CartItem::stockId).collect(Collectors.toList());
        List<Stock> stocks = stockIds.stream().map(stockId -> stockQueryService.findById(new StockId(stockId))).collect(Collectors.toList());

        List<String> productIds = stocks.stream().map(Stock::productId).collect(Collectors.toList());
        List<Product> products = productIds.stream()
                .map(productId -> productQueryService.findById(new ProductId(productId)))
                .collect(Collectors.toList());

        List<String> productNames = products.stream().map(Product::name).collect(Collectors.toList());
        List<Double> prices = products.stream().map(Product::price).collect(Collectors.toList());
        List<Double> promotionalPrices = products.stream().map(Product::promotionalPrice).collect(Collectors.toList());

        List<String> colorIds = stocks.stream().map(Stock::colorId).collect(Collectors.toList());
        List<String> colorNames = colorIds.stream()
                .map(colorId -> colorQueryService.findById(new ColorId(colorId)).name())
                .collect(Collectors.toList());

        List<String> sizeIds = stocks.stream().map(Stock::sizeId).collect(Collectors.toList());
        List<String> sizeNames = sizeIds.stream()
                .map(sizeId -> sizeQueryService.findById(new SizeId(sizeId)).name())
                .collect(Collectors.toList());

        List<CartItem> newCartItems = new ArrayList<>();
        for (int i = 0; i < cartItems.size(); i++){
            newCartItems.add(
                    cartItemMapper.updateDto(cartItems.get(i), productNames.get(i), colorNames.get(i), sizeNames.get(i),
                            prices.get(i), promotionalPrices.get(i))
            );
        }
        return newCartItems;
    }

    @Transactional
    public void insertToCart(String stockId, Long quantity, String cartToken){
        String cartId = this.getCartId(cartToken);
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
    public void updateQuantity(String stockId, Long quantity, String cartToken) {
        String cartId = this.getCartId(cartToken);
        Cart cart = cartQueryService.findById(new CartId(cartId));
        List<CartItem> cartItems = cart.cartItems();
        cartItems = this.updateQuantity(cartItems, stockId, quantity);
        cart = cartMapper.updateCartItems(cart, cartItems);
        cartCommandService.save(cart);
    }
    public String getCartId(String cartToken) {
        if (cartToken == null || jwtUtils.validateJwtToken(cartToken) == false) {
            throw new MyForbiddenException();
        }
        String cartId = null;
        try {
            cartId = jwtUtils.getClaimsFromJwtToken(cartToken).getStringClaim("cartId");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
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
    public void deleteCartItem(StockId stockId, String cartToken) {
        String cartId = this.getCartId(cartToken);
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

    @Transactional
    public String save() {
        Cart cart = Cart.builder().isDeleted(false).isActive(true).build();
        cart = cartCommandService.save(cart);
        String cartToken = jwtUtils.generateTokenFromCartId(cart.id());
        return cartToken;
    }

    public void addCartItems(List<CartItem> cartItemList, String cartToken) {
        String cartId = this.getCartId(cartToken);
        Cart cart = cartQueryService.findById(new CartId(cartId));
        cart = cartMapper.updateCartItems(cart, cartItemList);
        cartCommandService.save(cart);
    }
}

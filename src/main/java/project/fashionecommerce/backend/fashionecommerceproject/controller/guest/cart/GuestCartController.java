package project.fashionecommerce.backend.fashionecommerceproject.controller.guest.cart;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import project.fashionecommerce.backend.fashionecommerceproject.controller.guest.cart.models.CartTokenResponse;
import project.fashionecommerce.backend.fashionecommerceproject.controller.guest.cart.items.models.GuestCartItemResponse;
import project.fashionecommerce.backend.fashionecommerceproject.controller.guest.cart.items.models.GuestCartItemModelMapper;
import project.fashionecommerce.backend.fashionecommerceproject.controller.guest.cart.models.GuestCartRequest;
import project.fashionecommerce.backend.fashionecommerceproject.dto.cart.items.CartItem;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.StockId;
import project.fashionecommerce.backend.fashionecommerceproject.service.guest.GuestCartUseCaseService;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class GuestCartController implements GuestCartAPI {
    @NonNull
    final GuestCartUseCaseService guestCartUseCaseService;
    @NonNull
    final GuestCartItemModelMapper guestCartItemModelMapper;

    @Override
    public ResponseEntity<?> save() {
        String cartJWT = guestCartUseCaseService.save();
        return ResponseEntity.ok().body(new CartTokenResponse(cartJWT));
    }

    @Override
    public ResponseEntity<List<GuestCartItemResponse>> findAllCartItem(GuestCartRequest guestCartRequest) {
        List<CartItem> cartItems = guestCartUseCaseService.findAllCartItem(guestCartRequest.cartToken());
        return new ResponseEntity<>(cartItems.stream().map(guestCartItemModelMapper::toModel).collect(Collectors.toList()),
                HttpStatus.OK
        );
    }

    @Override
    public void deleteCartItem(String stockId, GuestCartRequest guestCartRequest) {
        guestCartUseCaseService.deleteCartItem(new StockId(stockId), guestCartRequest.cartToken());
    }

    @Override
    public void addCartItems(GuestCartRequest guestCartRequest) {
        List<CartItem> cartItemList = guestCartRequest.cartItems().stream().map(item -> guestCartItemModelMapper.toDto(item))
                .collect(Collectors.toList());
        guestCartUseCaseService.addCartItems(cartItemList, guestCartRequest.cartToken());
    }

    @Override
    public void insertToCart(String stockId, Long quantity, GuestCartRequest guestCartRequest) {
        guestCartUseCaseService.insertToCart(stockId, quantity, guestCartRequest.cartToken());
    }

    @Override
    public void updateQuantity(String stockId, Long quantity, GuestCartRequest guestCartRequest) {
        guestCartUseCaseService.updateQuantity(stockId, quantity, guestCartRequest.cartToken());
    }

}

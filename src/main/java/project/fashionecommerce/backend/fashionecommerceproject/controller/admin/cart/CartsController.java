package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.cart;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import project.fashionecommerce.backend.fashionecommerceproject.common.models.PageResponse;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.cart.models.CartModelMapper;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.cart.models.CartRequest;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.cart.models.CartResponse;
import project.fashionecommerce.backend.fashionecommerceproject.dto.cart.Cart;
import project.fashionecommerce.backend.fashionecommerceproject.dto.cart.CartQuery;
import project.fashionecommerce.backend.fashionecommerceproject.dto.handler.MySortHandler;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.cart.CartUseCaseService;

@RestController
@RequiredArgsConstructor
public class CartsController implements CartsAPI {
    @NonNull final CartUseCaseService cartUseCaseService;
    @NonNull final CartModelMapper cartModelMapper;
    @Override
    public void save(CartRequest cartRequest) {
        Cart cart = cartModelMapper.toDto(cartRequest);
        cartUseCaseService.save(cart);
    }

    @Override
    public ResponseEntity<PageResponse<CartResponse>> findAll(String search, String sort, Integer currentPage, Integer pageSize) {
        CartQuery cartQuery = new CartQuery(search);
        PageRequest pageRequest = PageRequest.of(currentPage-1, pageSize, MySortHandler.of(sort));
        Page<Cart> cartPage = cartUseCaseService.findAll(cartQuery, pageRequest);
        PageResponse<CartResponse> cartResponsePageResponse = new PageResponse<>(cartPage.map(cartModelMapper::toModel));
        return new ResponseEntity<>(cartResponsePageResponse, HttpStatus.OK) ;
    }
}

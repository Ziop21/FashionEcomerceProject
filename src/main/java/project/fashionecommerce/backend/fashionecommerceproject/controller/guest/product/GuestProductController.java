package project.fashionecommerce.backend.fashionecommerceproject.controller.guest.product;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import project.fashionecommerce.backend.fashionecommerceproject.common.models.PageResponse;
import project.fashionecommerce.backend.fashionecommerceproject.controller.guest.product.models.GuestProductModelMapper;
import project.fashionecommerce.backend.fashionecommerceproject.controller.guest.product.models.GuestProductResponse;
import project.fashionecommerce.backend.fashionecommerceproject.dto.handler.MySortHandler;
import project.fashionecommerce.backend.fashionecommerceproject.dto.product.Product;
import project.fashionecommerce.backend.fashionecommerceproject.dto.product.ProductQuery;
import project.fashionecommerce.backend.fashionecommerceproject.service.guest.GuestProductUseCaseService;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class GuestProductController implements GuestProductAPI {
    @NonNull final GuestProductUseCaseService guestProductUseCaseService;
    @NonNull final GuestProductModelMapper guestProductModelMapper;
    @Override
    public ResponseEntity<PageResponse<GuestProductResponse>> findAll(String search, List<String> sizeIds, List<String> colorIds,
                                                        Long fromRating, Long toRating, Long fromPrice, Long toPrice,
                                                        LocalDate fromDate, LocalDate toDate, String sort, Integer pageCurrent,
                                                        Integer pageSize) {
        ProductQuery productQuery = ProductQuery.builder()
                .search(search).sizeIds(sizeIds).colorIds(colorIds).fromRating(fromRating).toRating(toRating)
                .fromPrice(fromPrice).toPrice(toPrice).fromDate(fromDate).toDate(toDate)
                .build();

        PageRequest pageRequest = PageRequest.of(pageCurrent - 1, pageSize, MySortHandler.of(sort));

        Page<Product> productPage = guestProductUseCaseService.findAllProduct(productQuery, pageRequest);

        PageResponse<GuestProductResponse> finalProducts = new PageResponse<>(productPage.map(guestProductModelMapper::toModel));
        return new ResponseEntity<>(finalProducts, HttpStatus.OK);
    }
}

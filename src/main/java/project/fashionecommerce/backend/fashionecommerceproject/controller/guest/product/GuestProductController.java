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
import project.fashionecommerce.backend.fashionecommerceproject.dto.product.ProductId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.product.ProductQuery;
import project.fashionecommerce.backend.fashionecommerceproject.service.guest.GuestProductUseCaseService;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class GuestProductController implements GuestProductAPI {
    @NonNull final GuestProductUseCaseService guestProductUseCaseService;
    @NonNull final GuestProductModelMapper guestProductModelMapper;

    @Override
    public ResponseEntity<GuestProductResponse> findById(String productId) {
        Product product = guestProductUseCaseService.findById(new ProductId(productId));
        return new ResponseEntity<>(guestProductModelMapper.toModel(product), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<ProductId>> findAllProductIds(String search, List<String> categoryIds,
    List<String> sizeIds, List<String> colorIds, Long fromRating, Long toRating, Long fromPrice, Long toPrice,
    String sort, Integer currentPage, Integer pageSize) {

        ProductQuery productQuery = ProductQuery.builder()
                .search(search).categoryIds(categoryIds).sizeIds(sizeIds).colorIds(colorIds)
                .fromRating(fromRating).toRating(toRating)
                .fromPrice(fromPrice).toPrice(toPrice)
                .build();

        PageRequest pageRequest = PageRequest.of(currentPage - 1, pageSize, MySortHandler.of(sort));

        List<ProductId> productIdList = guestProductUseCaseService.findAllProductIds(productQuery, pageRequest);

        return new ResponseEntity<>(productIdList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PageResponse<GuestProductResponse>> findAll(String search, List<String> categoryIds, List<String> sizeIds, List<String> colorIds,
                                                        Long fromRating, Long toRating, Long fromPrice, Long toPrice, String sort, Integer currentPage,
                                                        Integer pageSize) {
        ProductQuery productQuery = ProductQuery.builder()
                .search(search).categoryIds(categoryIds).sizeIds(sizeIds).colorIds(colorIds)
                .fromRating(fromRating).toRating(toRating)
                .fromPrice(fromPrice).toPrice(toPrice)
                .build();

        PageRequest pageRequest = PageRequest.of(currentPage - 1, pageSize, MySortHandler.of(sort));

        Page<Product> productPage = guestProductUseCaseService.findAllProduct(productQuery, pageRequest);

        PageResponse<GuestProductResponse> finalProducts = new PageResponse<>(productPage.map(guestProductModelMapper::toModel));
        return new ResponseEntity<>(finalProducts, HttpStatus.OK);
    }
}

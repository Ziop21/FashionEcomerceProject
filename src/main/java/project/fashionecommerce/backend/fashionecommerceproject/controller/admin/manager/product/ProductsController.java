package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.product;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import project.fashionecommerce.backend.fashionecommerceproject.common.models.PageResponse;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.product.models.ProductModelMapper;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.product.models.ProductRequest;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.product.models.ProductResponse;
import project.fashionecommerce.backend.fashionecommerceproject.dto.handler.MySortHandler;
import project.fashionecommerce.backend.fashionecommerceproject.dto.product.Product;
import project.fashionecommerce.backend.fashionecommerceproject.dto.product.ProductQuery;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.product.ProductUseCaseService;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductsController implements ProductsAPI{
    @NonNull final ProductModelMapper productModelMapper;
    @NonNull final ProductUseCaseService productUseCaseService;
    @Override
    public void save(ProductRequest productRequest) {
        Product product = productModelMapper.toDto(productRequest);
        productUseCaseService.save(product);
    }

    @Override
    public ResponseEntity<PageResponse<ProductResponse>> findAll(String search, List<String> sizeIds, List<String> colorIds,
                                                                 Long fromRating, Long toRating, Long fromPrice, Long toPrice,
                                                                 LocalDate fromDate, LocalDate toDate, String sort, Integer pageCurrent,
                                                                 Integer pageSize) {
        ProductQuery productQuery = ProductQuery.builder()
                .search(search)
                .sizeIds(sizeIds)
                .colorIds(colorIds)
                .fromRating(toRating).toRating(toRating)
                .fromPrice(fromPrice).toPrice(toPrice)
                .fromDate(fromDate).toDate(toDate)
                .build();

        PageRequest pageRequest = PageRequest.of(pageCurrent - 1, pageSize, MySortHandler.of(sort));

        Page<Product> productPage = productUseCaseService.findAll(productQuery, pageRequest);

        PageResponse<ProductResponse> productResponsePageResponse = new PageResponse<>(productPage.map(productModelMapper::toModel));

        return new ResponseEntity<>(productResponsePageResponse, HttpStatus.OK);
    }
}

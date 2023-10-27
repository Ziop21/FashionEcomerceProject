package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.product;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import project.fashionecommerce.backend.fashionecommerceproject.common.models.PageResponse;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.product.models.ProductModelMapper;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.product.models.ProductRequest;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.product.models.ProductResponse;
import project.fashionecommerce.backend.fashionecommerceproject.dto.handler.MySortHandler;
import project.fashionecommerce.backend.fashionecommerceproject.dto.product.Product;
import project.fashionecommerce.backend.fashionecommerceproject.dto.product.ProductQuery;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.product.ProductUseCaseService;
import java.time.LocalDate;

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
    public ResponseEntity<PageResponse<ProductResponse>> findAll(String search, LocalDate fromDate, LocalDate toDate, String sort, Integer pageCurrent, Integer pageSize) {
        ProductQuery productQuery = ProductQuery.builder().search(search)
                .fromDate(fromDate)
                .toDate(toDate)
                .build();

        PageRequest pageRequest = PageRequest.of(pageCurrent - 1, pageSize, MySortHandler.of(sort));

        Page<Product> productPage = productUseCaseService.findAll(productQuery, pageRequest);

        PageResponse<ProductResponse> productResponsePageResponse = new PageResponse<>(productPage.map(productModelMapper::toModel));

        return new ResponseEntity<>(productResponsePageResponse, HttpStatus.OK);
    }
}

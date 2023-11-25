package project.fashionecommerce.backend.fashionecommerceproject.controller.staff.product;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import project.fashionecommerce.backend.fashionecommerceproject.common.models.PageResponse;
import project.fashionecommerce.backend.fashionecommerceproject.controller.staff.product.models.StaffProductModelMapper;
import project.fashionecommerce.backend.fashionecommerceproject.controller.staff.product.models.StaffProductRequest;
import project.fashionecommerce.backend.fashionecommerceproject.controller.staff.product.models.StaffProductResponse;
import project.fashionecommerce.backend.fashionecommerceproject.dto.handler.MySortHandler;
import project.fashionecommerce.backend.fashionecommerceproject.dto.product.Product;
import project.fashionecommerce.backend.fashionecommerceproject.dto.product.ProductQuery;
import project.fashionecommerce.backend.fashionecommerceproject.service.staff.StaffProductUseCaseService;

@RestController
@RequiredArgsConstructor
public class StaffProductsController implements StaffProductsAPI {
    @NonNull final StaffProductUseCaseService staffProductUseCaseService;
    @NonNull final StaffProductModelMapper staffProductModelMapper;
    @Override
    public void save(StaffProductRequest staffProductRequest) {
        Product product = staffProductModelMapper.toDto(staffProductRequest);
        staffProductUseCaseService.save(product);
    }

    @Override
    public ResponseEntity<PageResponse<StaffProductResponse>> findAll(String search, String sort, Integer currentPage, Integer pageSize) {
        ProductQuery productQuery = ProductQuery
                .builder()
                .search(search)
                .build();
        PageRequest pageRequest = PageRequest.of(currentPage - 1, pageSize, MySortHandler.of(sort));

        Page<Product> productPage = staffProductUseCaseService.findAll(productQuery, pageRequest);

        PageResponse<StaffProductResponse> finalProducts = new PageResponse<>(productPage.map(staffProductModelMapper::toModel));

        return new ResponseEntity<>(finalProducts, HttpStatus.OK);
    }
}

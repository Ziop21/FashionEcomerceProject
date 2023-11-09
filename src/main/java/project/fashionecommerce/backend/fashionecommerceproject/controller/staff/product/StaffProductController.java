package project.fashionecommerce.backend.fashionecommerceproject.controller.staff.product;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import project.fashionecommerce.backend.fashionecommerceproject.controller.staff.product.models.StaffProductModelMapper;
import project.fashionecommerce.backend.fashionecommerceproject.controller.staff.product.models.StaffProductRequest;
import project.fashionecommerce.backend.fashionecommerceproject.controller.staff.product.models.StaffProductResponse;
import project.fashionecommerce.backend.fashionecommerceproject.dto.product.Product;
import project.fashionecommerce.backend.fashionecommerceproject.dto.product.ProductId;
import project.fashionecommerce.backend.fashionecommerceproject.service.staff.StaffProductUseCaseService;

@RestController
@RequiredArgsConstructor
public class StaffProductController implements StaffProductAPI {
    @NonNull final StaffProductUseCaseService staffProductUseCaseService;
    @NonNull final StaffProductModelMapper staffProductModelMapper;

    @Override
    public void update(String productId, StaffProductRequest staffProductRequest) {
        Product product = staffProductModelMapper.toDto(staffProductRequest);
        staffProductUseCaseService.update(new ProductId(productId), product);
    }

    @Override
    public ResponseEntity<StaffProductResponse> findById(String productId) {
        Product product = staffProductUseCaseService.findById(new ProductId(productId));
        StaffProductResponse staffProductResponse = staffProductModelMapper.toModel(product);
        return new ResponseEntity<>(staffProductResponse, HttpStatus.OK);
    }

    @Override
    public void delete(String productId) {
        staffProductUseCaseService.delete(new ProductId(productId));
    }
}

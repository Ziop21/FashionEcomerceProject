package project.fashionecommerce.backend.fashionecommerceproject.controller.staff.product;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import project.fashionecommerce.backend.fashionecommerceproject.controller.staff.product.models.StaffProductModelMapper;
import project.fashionecommerce.backend.fashionecommerceproject.controller.staff.product.models.StaffProductRequest;
import project.fashionecommerce.backend.fashionecommerceproject.dto.product.Product;
import project.fashionecommerce.backend.fashionecommerceproject.service.staff.StaffProductUseCaseService;

@RestController
@RequiredArgsConstructor
public class StaffProductController implements StaffProductAPI {
    @NonNull final StaffProductUseCaseService staffProductUseCaseService;
    @NonNull final StaffProductModelMapper staffProductModelMapper;
    @Override
    public void save(StaffProductRequest staffProductRequest) {
        Product product = staffProductModelMapper.toDto(staffProductRequest);
        staffProductUseCaseService.save(product);
    }
}

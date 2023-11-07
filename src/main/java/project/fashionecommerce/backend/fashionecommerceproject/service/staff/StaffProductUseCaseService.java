package project.fashionecommerce.backend.fashionecommerceproject.service.staff;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.fashionecommerce.backend.fashionecommerceproject.dto.product.Product;
import project.fashionecommerce.backend.fashionecommerceproject.dto.product.ProductMapper;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.product.ProductCommmandService;

@Service
@RequiredArgsConstructor
public class StaffProductUseCaseService {
    @NonNull final ProductCommmandService productCommmandService;

    @NonNull final ProductMapper productMapper;

    public void save(Product product) {
        product = productMapper.updateDtoIsActive(product, false);
        product = productMapper.updateDtoIsDeleted(product, false);
        productCommmandService.save(product);
    }
}

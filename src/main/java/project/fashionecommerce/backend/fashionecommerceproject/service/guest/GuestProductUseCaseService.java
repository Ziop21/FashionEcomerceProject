package project.fashionecommerce.backend.fashionecommerceproject.service.guest;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import project.fashionecommerce.backend.fashionecommerceproject.dto.enums.ERole;
import project.fashionecommerce.backend.fashionecommerceproject.dto.product.Product;
import project.fashionecommerce.backend.fashionecommerceproject.dto.product.ProductQuery;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.product.ProductQueryService;

@Service
@RequiredArgsConstructor
public class GuestProductUseCaseService {
    @NonNull final ProductQueryService productQueryService;
    public Page<Product> findAllProduct(ProductQuery productQuery, PageRequest pageRequest) {
        Page<Product> productPage = productQueryService.findAll(productQuery, pageRequest, ERole.GUEST);
        return productPage;
    }
}

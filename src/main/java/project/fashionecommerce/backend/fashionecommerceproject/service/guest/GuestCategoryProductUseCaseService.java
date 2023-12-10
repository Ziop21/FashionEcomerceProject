package project.fashionecommerce.backend.fashionecommerceproject.service.guest;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import project.fashionecommerce.backend.fashionecommerceproject.dto.category.product.CategoryProduct;
import project.fashionecommerce.backend.fashionecommerceproject.dto.category.product.CategoryProductQuery;
import project.fashionecommerce.backend.fashionecommerceproject.dto.enums.ERole;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.category.product.CategoryProductQueryService;

@Service
@RequiredArgsConstructor
public class GuestCategoryProductUseCaseService {
    @NonNull final CategoryProductQueryService categoryProductQueryService;

    public Page<CategoryProduct> findAllByProductId(CategoryProductQuery query, PageRequest pageRequest) {
        Page<CategoryProduct> categoryProducts = categoryProductQueryService.findAllByProductId(query, pageRequest, ERole.GUEST);
        return  categoryProducts;
    }
}

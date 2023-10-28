package project.fashionecommerce.backend.fashionecommerceproject.service.database.category.product;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.fashionecommerce.backend.fashionecommerceproject.dto.category.product.CategoryProduct;
import project.fashionecommerce.backend.fashionecommerceproject.dto.category.product.CategoryProductId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.category.product.CategoryProductQuery;

@Service
@RequiredArgsConstructor
public class CategoryProductUseCaseService {
    @NonNull
    final CategoryProductCommandService categoryProductCommandService;
    @NonNull
    final CategoryProductQueryService categoryProductQueryService;
    @Transactional
    public void save(CategoryProduct categoryProduct) {
        categoryProductCommandService.save(categoryProduct);
    }

    @Transactional
    public void update(CategoryProductId categoryProductId, CategoryProduct categoryProduct) {
        categoryProductCommandService.update(categoryProductId, categoryProduct);
    }
    @Transactional
    public void delete(CategoryProductId categoryProductId) {
        categoryProductCommandService.delete(categoryProductId);
    }

    public CategoryProduct findById(CategoryProductId categoryProductId) {
        return categoryProductQueryService.findById(categoryProductId);
    }

    public Page<CategoryProduct> findAll(CategoryProductQuery categoryProductQuery, PageRequest pageRequest) {
        return categoryProductQueryService.findAll(categoryProductQuery, pageRequest);
    }
}

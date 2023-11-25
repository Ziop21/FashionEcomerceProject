package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.category.product;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.category.product.models.CategoryProductModelMapper;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.category.product.models.CategoryProductRequest;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.category.product.models.CategoryProductResponse;
import project.fashionecommerce.backend.fashionecommerceproject.dto.category.product.CategoryProductId;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.category.product.CategoryProductUseCaseService;
import project.fashionecommerce.backend.fashionecommerceproject.dto.category.product.CategoryProduct;
@RestController
@RequiredArgsConstructor
public class CategoryProductController implements CategoryProductAPI {
    @NonNull
    final CategoryProductUseCaseService categoryProductUseCaseService;
    @NonNull
    final CategoryProductModelMapper categoryProductModelMapper;
    @Override
    public void update(String categoryProductId, CategoryProductRequest categoryProductRequest) {
        CategoryProduct categoryProduct = categoryProductModelMapper.toDto(categoryProductRequest);
        categoryProductUseCaseService.update(new CategoryProductId(categoryProductId), categoryProduct);
    }

    @Override
    public ResponseEntity<CategoryProductResponse> findById(String categoryProductId) {
        CategoryProduct categoryProduct = categoryProductUseCaseService.findById(new CategoryProductId(categoryProductId));
        CategoryProductResponse categoryProductResponse = categoryProductModelMapper.toModel(categoryProduct);
        return new ResponseEntity<>(categoryProductResponse, HttpStatus.OK);
    }

    @Override
    public void delete(String categoryProductId) {
        categoryProductUseCaseService.delete(new CategoryProductId(categoryProductId));
    }
}

package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.category;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.category.models.CategoryModelMapper;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.category.models.CategoryRequest;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.category.models.CategoryResponse;
import project.fashionecommerce.backend.fashionecommerceproject.dto.category.Category;
import project.fashionecommerce.backend.fashionecommerceproject.dto.category.CategoryId;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.category.CategoryUseCaseService;

@RestController
@RequiredArgsConstructor
public class CategoryController implements CategoryAPI{
    @NonNull final CategoryUseCaseService categoryUseCaseService;
    @NonNull final CategoryModelMapper categoryModelMapper;

    @Override
    public ResponseEntity<CategoryResponse> findById(String categoryId) {
        Category category = categoryUseCaseService.findById(new CategoryId(categoryId));
        return new ResponseEntity<>(categoryModelMapper.toModel(category), HttpStatus.OK);
    }

    @Override
    public void delete(String categoryId) {
        categoryUseCaseService.delete(new CategoryId(categoryId));
    }

    @Override
    public void update(String categoryId, CategoryRequest categoryRequest) {
        Category category = categoryModelMapper.toDto(categoryRequest);
        categoryUseCaseService.update(new CategoryId(categoryId), category);
    }
}

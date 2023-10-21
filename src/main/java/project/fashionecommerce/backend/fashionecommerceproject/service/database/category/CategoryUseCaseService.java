package project.fashionecommerce.backend.fashionecommerceproject.service.database.category;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.fashionecommerce.backend.fashionecommerceproject.dto.category.Category;
import project.fashionecommerce.backend.fashionecommerceproject.dto.category.CategoryId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.category.CategoryQuery;

@Service
@RequiredArgsConstructor
public class CategoryUseCaseService {
    @NonNull final CategoryCommandService categoryCommandService;
    @NonNull final CategoryQueryService categoryQueryService;
    @Transactional
    public void update(CategoryId categoryId, Category category) {
        categoryCommandService.update(categoryId, category);
    }
    public void delete(CategoryId categoryId) {
        categoryCommandService.delete(categoryId);
    }
    @Transactional
    public void save(Category category) {
        categoryCommandService.save(category);
    }

    public Page<Category> findAll(CategoryQuery categoryQuery, PageRequest pageRequest) {
        return categoryQueryService.findAll(categoryQuery, pageRequest);
    }

    public Category findById(CategoryId categoryId) {
        return categoryQueryService.findById(categoryId);
    }

}

package project.fashionecomerce.backend.fashionecomerceproject.controller.User.Category;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.fashionecomerce.backend.fashionecomerceproject.controller.User.Category.models.CategoryRequest;
import project.fashionecomerce.backend.fashionecomerceproject.controller.User.Category.models.CategoryResponse;
import project.fashionecomerce.backend.fashionecomerceproject.dto.User.Category.Category;
import project.fashionecomerce.backend.fashionecomerceproject.service.User.Category.CategoryUseCaseService;

@RestController
@RequiredArgsConstructor
public class CategoriesController implements CategoriesAPI{
    @NonNull
    final CategoryUseCaseService categoryUseCaseService;
    @Override
    public void create(@RequestBody CategoryRequest categoryRequest) {
        Category category = mapper.toDTO(categoryRequest);
        categoryUseCaseService.save(category);
    }

    @Override
    public PageImpl<CategoryResponse> findAll() {
        return null;
    }
}

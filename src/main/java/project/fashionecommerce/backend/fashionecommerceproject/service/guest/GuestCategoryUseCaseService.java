package project.fashionecommerce.backend.fashionecommerceproject.service.guest;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import project.fashionecommerce.backend.fashionecommerceproject.dto.category.Category;
import project.fashionecommerce.backend.fashionecommerceproject.dto.category.CategoryId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.category.CategoryQuery;
import project.fashionecommerce.backend.fashionecommerceproject.dto.enums.ERole;
import project.fashionecommerce.backend.fashionecommerceproject.exception.MyResourceNotFoundException;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.category.CategoryQueryService;

@Service
@RequiredArgsConstructor
public class GuestCategoryUseCaseService {
    @NonNull
    final CategoryQueryService categoryQueryService;

    public Page<Category> findAll(CategoryQuery categoryQuery, PageRequest pageRequest) {
        return categoryQueryService.findAll(categoryQuery, pageRequest, ERole.GUEST);
    }

    public Category findById(CategoryId categoryId) {
        Category category = categoryQueryService.findById(categoryId);
        if (category.isDeleted() || !category.isActive())
            throw new MyResourceNotFoundException();
        return category;
    }
}

package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.category;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import project.fashionecommerce.backend.fashionecommerceproject.common.models.PageResponse;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.category.models.CategoryModelMapper;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.category.models.CategoryRequest;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.category.models.CategoryResponse;
import project.fashionecommerce.backend.fashionecommerceproject.dto.category.Category;
import project.fashionecommerce.backend.fashionecommerceproject.dto.category.CategoryQuery;
import project.fashionecommerce.backend.fashionecommerceproject.dto.handler.MySortHandler;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.category.CategoryUseCaseService;

@RestController
@RequiredArgsConstructor
public class CategoriesController implements CategoriesAPI{
    @NonNull final CategoryUseCaseService categoryUseCaseService;
    @NonNull final CategoryModelMapper categoryModelMapper;

    @Override
    public void save(CategoryRequest categoryRequest) {
        Category category = categoryModelMapper.toDto(categoryRequest);
        categoryUseCaseService.save(category);
    }

    @Override
    public ResponseEntity<PageResponse<CategoryResponse>> findAll(String search, String sort, Integer currentPage, Integer pageSize) {
        CategoryQuery categoryQuery = new CategoryQuery(search);
        PageRequest pageRequest = PageRequest.of(currentPage-1, pageSize, MySortHandler.of(sort));
        Page<Category> categoryPage = categoryUseCaseService.findAll(categoryQuery, pageRequest);
        PageResponse<CategoryResponse> categoryResponsePageResponse = new PageResponse<>(categoryPage.map(categoryModelMapper::toModel));
        return new ResponseEntity<>(categoryResponsePageResponse, HttpStatus.OK);
    }
}

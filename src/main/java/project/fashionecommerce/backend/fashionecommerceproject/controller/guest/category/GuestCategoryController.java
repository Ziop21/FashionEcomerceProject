package project.fashionecommerce.backend.fashionecommerceproject.controller.guest.category;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import project.fashionecommerce.backend.fashionecommerceproject.common.models.PageResponse;
import project.fashionecommerce.backend.fashionecommerceproject.controller.guest.category.models.GuestCategoryModelMapper;
import project.fashionecommerce.backend.fashionecommerceproject.controller.guest.category.models.GuestCategoryResponse;
import project.fashionecommerce.backend.fashionecommerceproject.controller.guest.category.models.GuestCategoryResponse;
import project.fashionecommerce.backend.fashionecommerceproject.dto.category.Category;
import project.fashionecommerce.backend.fashionecommerceproject.dto.category.CategoryId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.category.CategoryQuery;
import project.fashionecommerce.backend.fashionecommerceproject.dto.handler.MySortHandler;
import project.fashionecommerce.backend.fashionecommerceproject.dto.category.Category;
import project.fashionecommerce.backend.fashionecommerceproject.dto.category.CategoryQuery;
import project.fashionecommerce.backend.fashionecommerceproject.service.guest.GuestCategoryUseCaseService;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class GuestCategoryController implements GuestCategoryAPI {
    @NonNull
    final GuestCategoryUseCaseService guestCategoryUseCaseService;
    @NonNull
    final GuestCategoryModelMapper guestCategoryModelMapper;

    @Override
    public ResponseEntity<GuestCategoryResponse> findById(String categoryId) {
        Category category = guestCategoryUseCaseService.findById(new CategoryId(categoryId));
        return new ResponseEntity<GuestCategoryResponse>(guestCategoryModelMapper.toModel(category), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PageResponse<GuestCategoryResponse>> findAll(String search, String sort, Integer currentPage, Integer pageSize) {
        CategoryQuery categoryQuery = CategoryQuery.builder()
                .search(search)
                .build();

        PageRequest pageRequest = PageRequest.of(currentPage - 1, pageSize, MySortHandler.of(sort));

        Page<Category> categoryPage = guestCategoryUseCaseService.findAll(categoryQuery, pageRequest);

        PageResponse<GuestCategoryResponse> finalCategories = new PageResponse<>(categoryPage.map(guestCategoryModelMapper::toModel));
        return new ResponseEntity<>(finalCategories, HttpStatus.OK);
    }
}

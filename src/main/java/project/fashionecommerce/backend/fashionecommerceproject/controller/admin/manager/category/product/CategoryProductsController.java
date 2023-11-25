package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.category.product;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import project.fashionecommerce.backend.fashionecommerceproject.common.models.PageResponse;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.category.product.models.CategoryProductModelMapper;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.category.product.models.CategoryProductRequest;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.category.product.models.CategoryProductResponse;
import project.fashionecommerce.backend.fashionecommerceproject.dto.category.product.CategoryProduct;
import project.fashionecommerce.backend.fashionecommerceproject.dto.category.product.CategoryProductQuery;
import project.fashionecommerce.backend.fashionecommerceproject.dto.handler.MySortHandler;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.category.product.CategoryProductUseCaseService;
import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class CategoryProductsController implements CategoryProductsAPI {
    @NonNull
    final CategoryProductUseCaseService categoryProductUseCaseService;
    @NonNull
    final CategoryProductModelMapper categoryProductModelMapper;
    @Override
    public void save(CategoryProductRequest categoryProductRequest) {
        CategoryProduct categoryProduct = categoryProductModelMapper.toDto(categoryProductRequest);
        categoryProductUseCaseService.save(categoryProduct);
    }

    @Override
    public ResponseEntity<PageResponse<CategoryProductResponse>> findAll(String search, LocalDate fromDate,
                                                                         LocalDate toDate, String sort,
                                                                         Integer pageCurrent, Integer pageCategoryProduct) {
        CategoryProductQuery categoryProductQuery = CategoryProductQuery.builder()
                .search(search)
                .fromDate(fromDate)
                .toDate(toDate)
                .build();
        PageRequest pageRequest = PageRequest.of(pageCurrent - 1, pageCategoryProduct, MySortHandler.of(sort));

        Page<CategoryProduct> categoryProductPage = categoryProductUseCaseService.findAll(categoryProductQuery, pageRequest);

        PageResponse<CategoryProductResponse> categoryProductResponsePage = new PageResponse<>(categoryProductPage.map(categoryProductModelMapper::toModel));
        return new ResponseEntity<>(categoryProductResponsePage, HttpStatus.OK);
    }
}

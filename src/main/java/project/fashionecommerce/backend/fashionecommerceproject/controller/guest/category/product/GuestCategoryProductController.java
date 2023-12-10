package project.fashionecommerce.backend.fashionecommerceproject.controller.guest.category.product;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import project.fashionecommerce.backend.fashionecommerceproject.common.models.PageResponse;
import project.fashionecommerce.backend.fashionecommerceproject.controller.guest.category.product.models.GuestCategoryProductModel;
import project.fashionecommerce.backend.fashionecommerceproject.controller.guest.category.product.models.GuestCategoryProductModelMapper;
import project.fashionecommerce.backend.fashionecommerceproject.dto.category.product.CategoryProduct;
import project.fashionecommerce.backend.fashionecommerceproject.dto.category.product.CategoryProductQuery;
import project.fashionecommerce.backend.fashionecommerceproject.dto.handler.MySortHandler;
import project.fashionecommerce.backend.fashionecommerceproject.service.guest.GuestCategoryProductUseCaseService;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class GuestCategoryProductController implements GuestCategoryProductAPI{
    @NonNull final GuestCategoryProductUseCaseService guestCategoryProductUseCaseService;
    @NonNull final GuestCategoryProductModelMapper guestCategoryProductModelMapper;
    @Override
    public ResponseEntity<PageResponse<GuestCategoryProductModel>> findAlByProductId(String productId, String search,
    LocalDate fromDate, LocalDate toDate, String sort, Integer currentPage, Integer pageSize) {
        CategoryProductQuery query = CategoryProductQuery.builder()
                .productId(productId)
                .search(search)
                .fromDate(fromDate)
                .toDate(toDate)
                .build();
        PageRequest pageRequest = PageRequest.of(currentPage - 1, pageSize, MySortHandler.of(sort));

        Page<CategoryProduct> categoryProducts = guestCategoryProductUseCaseService.findAllByProductId(query, pageRequest);

        PageResponse<GuestCategoryProductModel> finalCategories = new PageResponse<>(categoryProducts.map(guestCategoryProductModelMapper::toModel));
        return new ResponseEntity<>(finalCategories, HttpStatus.OK);
    }
}

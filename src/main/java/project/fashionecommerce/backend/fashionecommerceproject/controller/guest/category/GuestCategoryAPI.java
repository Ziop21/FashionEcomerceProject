package project.fashionecommerce.backend.fashionecommerceproject.controller.guest.category;

import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.fashionecommerce.backend.fashionecommerceproject.common.models.PageResponse;
import project.fashionecommerce.backend.fashionecommerceproject.controller.guest.category.models.GuestCategoryResponse;

@RequestMapping("/api/guest/category")
public interface GuestCategoryAPI {
    @GetMapping("/{categoryId}")
    ResponseEntity<GuestCategoryResponse> findById(
            @PathVariable String categoryId
    );

    @GetMapping
    ResponseEntity<PageResponse<GuestCategoryResponse>> findAll(
            @RequestParam(required = false, value = "search", defaultValue = "") String search,
            @RequestParam(required = false, value = "sort", defaultValue = "") String sort,
            @RequestParam(required = false, value = "currentPage", defaultValue = "1") @Min(1) Integer currentPage,
            @RequestParam(required = false, value = "pageSize", defaultValue = "20") Integer pageSize
    );
}

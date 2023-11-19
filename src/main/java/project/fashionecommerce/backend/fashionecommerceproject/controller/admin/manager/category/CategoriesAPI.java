package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.category;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.fashionecommerce.backend.fashionecommerceproject.common.models.PageResponse;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.category.models.CategoryRequest;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.category.models.CategoryResponse;

@RequestMapping("/api/admin/manager/category")
public interface CategoriesAPI {
    @PostMapping
    void save(@RequestBody @Valid CategoryRequest categoryRequest);

    @GetMapping
    ResponseEntity<PageResponse<CategoryResponse>> findAll(
            @RequestParam(required = false,value = "search", defaultValue = "") String search,
            @RequestParam(required = false,value = "sort", defaultValue = "") String sort,
            @RequestParam(required = false,value = "currentPage", defaultValue = "1") @Min(1) Integer currentPage,
            @RequestParam(required = false,value = "pageSize",defaultValue = "20") Integer pageSize
    );
}

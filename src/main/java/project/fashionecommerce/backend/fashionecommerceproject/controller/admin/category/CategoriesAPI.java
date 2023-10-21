package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.category;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.fashionecommerce.backend.fashionecommerceproject.common.models.PageResponse;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.category.models.CategoryRequest;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.category.models.CategoryResponse;

@RequestMapping("/api/admin/category")
public interface CategoriesAPI {
    @PostMapping
    void save(@RequestBody @Valid CategoryRequest categoryRequest);

    @GetMapping
    ResponseEntity<PageResponse<CategoryResponse>> findAll(
            @RequestParam(required = false,value = "searchString", defaultValue = "") String searchString,
            @RequestParam(required = false,value = "sort", defaultValue = "") String sort,
            @RequestParam(required = false,value = "pageCurrent", defaultValue = "1") @Min(1) Integer pageCurrent,
            @RequestParam(required = false,value = "pageSize",defaultValue = "20") Integer pageSize
    );
}

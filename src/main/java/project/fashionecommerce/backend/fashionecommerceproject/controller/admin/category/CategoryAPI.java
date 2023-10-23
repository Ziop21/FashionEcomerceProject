package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.category;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.category.models.CategoryRequest;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.category.models.CategoryResponse;

@RequestMapping("/api/admin/category/{categoryId}")
public interface CategoryAPI {
    @GetMapping
    ResponseEntity<CategoryResponse> findById(@PathVariable String categoryId);

    @DeleteMapping
    void delete(@PathVariable String categoryId);

    @PutMapping
    void update(@PathVariable String categoryId, @RequestBody @Valid CategoryRequest categoryRequest);
}

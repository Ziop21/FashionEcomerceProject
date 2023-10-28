package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.category.product;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.category.product.models.CategoryProductRequest;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.category.product.models.CategoryProductResponse;

@RequestMapping("/api/admin/category-product/{categoryProductId}")
public interface CategoryProductAPI {
    @PutMapping
    void update(@PathVariable String categoryProductId, @RequestBody @Valid CategoryProductRequest categoryProductRequest);

    @GetMapping
    ResponseEntity<CategoryProductResponse> findById(@PathVariable String categoryProductId);

    @DeleteMapping
    void delete(@PathVariable String categoryProductId);
}

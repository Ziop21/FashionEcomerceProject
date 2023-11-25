package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.product;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.product.models.ProductRequest;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.product.models.ProductResponse;

@RequestMapping("/api/admin/manager/product/{productId}")
public interface ProductAPI {
    @PutMapping
    void update(@PathVariable String productId, @RequestBody @Valid ProductRequest productRequest);

    @GetMapping
    ResponseEntity<ProductResponse> findById(@PathVariable String productId);

    @DeleteMapping
    void delete(@PathVariable String productId);
}

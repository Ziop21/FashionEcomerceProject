package project.fashionecommerce.backend.fashionecommerceproject.controller.staff.product;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import project.fashionecommerce.backend.fashionecommerceproject.controller.staff.product.models.StaffProductRequest;
import project.fashionecommerce.backend.fashionecommerceproject.controller.staff.product.models.StaffProductResponse;

@RequestMapping("/api/staff/product/{productId}")
public interface StaffProductAPI {
    @PutMapping
    void update(@PathVariable String productId, @RequestBody @Valid StaffProductRequest staffProductRequest);

    @GetMapping
    ResponseEntity<StaffProductResponse> findById(@PathVariable String productId);

    @DeleteMapping
    void delete(@PathVariable String productId);
}

package project.fashionecommerce.backend.fashionecommerceproject.controller.staff.product;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import project.fashionecommerce.backend.fashionecommerceproject.controller.staff.product.models.StaffProductRequest;

@RequestMapping("/api/staff/product")
public interface StaffProductAPI {
    @PostMapping
    void save(@RequestBody @Valid StaffProductRequest staffProductRequest);
}

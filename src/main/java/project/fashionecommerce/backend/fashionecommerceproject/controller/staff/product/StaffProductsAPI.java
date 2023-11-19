package project.fashionecommerce.backend.fashionecommerceproject.controller.staff.product;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.fashionecommerce.backend.fashionecommerceproject.common.models.PageResponse;
import project.fashionecommerce.backend.fashionecommerceproject.controller.staff.product.models.StaffProductRequest;
import project.fashionecommerce.backend.fashionecommerceproject.controller.staff.product.models.StaffProductResponse;

@RequestMapping("/api/staff/products")
public interface StaffProductsAPI {
    @PostMapping
    void save(@RequestBody @Valid StaffProductRequest staffProductRequest);

    @GetMapping
    ResponseEntity<PageResponse<StaffProductResponse>> findAll(
            @RequestParam(required = false,value = "search", defaultValue = "") String search,
            @RequestParam(required = false,value = "sort", defaultValue = "") String sort,
            @RequestParam(required = false,value = "currentPage", defaultValue = "1") @Min(1) Integer currentPage,
            @RequestParam(required = false,value = "pageSize",defaultValue = "20") Integer pageSize
    );
}

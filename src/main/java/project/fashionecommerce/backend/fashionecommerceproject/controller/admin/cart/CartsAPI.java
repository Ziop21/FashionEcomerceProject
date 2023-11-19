package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.cart;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.fashionecommerce.backend.fashionecommerceproject.common.models.PageResponse;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.cart.models.CartRequest;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.cart.models.CartResponse;

@RequestMapping("/api/admin/cart")
public interface CartsAPI {
    @PostMapping
    void save(@RequestBody @Valid CartRequest cartRequest);

    @GetMapping
    ResponseEntity<PageResponse<CartResponse>> findAll(
            @RequestParam(required = false,value = "search", defaultValue = "") String search,
            @RequestParam(required = false,value = "sort", defaultValue = "") String sort,
            @RequestParam(required = false,value = "currentPage", defaultValue = "1") @Min(1) Integer currentPage,
            @RequestParam(required = false,value = "pageSize",defaultValue = "20") Integer pageSize
    );
}

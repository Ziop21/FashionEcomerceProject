package project.fashionecommerce.backend.fashionecommerceproject.controller.guest.product;

import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.fashionecommerce.backend.fashionecommerceproject.common.models.PageResponse;
import project.fashionecommerce.backend.fashionecommerceproject.controller.guest.product.models.GuestProductResponse;
import project.fashionecommerce.backend.fashionecommerceproject.dto.product.ProductId;
import java.util.List;

@RequestMapping("/api/guest/product")
public interface GuestProductAPI {
    @GetMapping("/{productId}")
    ResponseEntity<GuestProductResponse> findById(@PathVariable String productId);

    @GetMapping("/productIds")
    ResponseEntity<List<ProductId>> findAllProductIds(
            @RequestParam(required = false, value = "search", defaultValue = "") String search,
            @RequestParam(required = false, value = "categoryIds", defaultValue = "") List<String> categoryIds,
            @RequestParam(required = false, value = "sizeIds", defaultValue = "") List<String> sizeIds,
            @RequestParam(required = false, value = "colorIds", defaultValue = "") List<String> colorIds,
            @RequestParam(required = false, value = "fromRating", defaultValue = "") Long fromRating,
            @RequestParam(required = false, value = "toRating", defaultValue = "") Long toRating,
            @RequestParam(required = false, value = "fromPrice", defaultValue = "") Long fromPrice,
            @RequestParam(required = false, value = "toPrice", defaultValue = "") Long toPrice,
            @RequestParam(required = false, value = "sort", defaultValue = "") String sort,
            @RequestParam(required = false, value = "currentPage", defaultValue = "1") @Min(1) Integer currentPage,
            @RequestParam(required = false, value = "pageSize", defaultValue = "20") Integer pageSize
    );

    @GetMapping
    ResponseEntity<PageResponse<GuestProductResponse>> findAll(
            @RequestParam(required = false, value = "search", defaultValue = "") String search,
            @RequestParam(required = false, value = "categoryIds", defaultValue = "") List<String> categoryIds,
            @RequestParam(required = false, value = "sizeIds", defaultValue = "") List<String> sizeIds,
            @RequestParam(required = false, value = "colorIds", defaultValue = "") List<String> colorIds,
            @RequestParam(required = false, value = "fromRating", defaultValue = "") Long fromRating,
            @RequestParam(required = false, value = "toRating", defaultValue = "") Long toRating,
            @RequestParam(required = false, value = "fromPrice", defaultValue = "") Long fromPrice,
            @RequestParam(required = false, value = "toPrice", defaultValue = "") Long toPrice,
            @RequestParam(required = false, value = "sort", defaultValue = "") String sort,
            @RequestParam(required = false, value = "currentPage", defaultValue = "1") @Min(1) Integer currentPage,
            @RequestParam(required = false, value = "pageSize", defaultValue = "20") Integer pageSize
    );
}

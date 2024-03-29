package project.fashionecommerce.backend.fashionecommerceproject.controller.guest.stock;

import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.fashionecommerce.backend.fashionecommerceproject.common.models.PageResponse;
import project.fashionecommerce.backend.fashionecommerceproject.controller.guest.stock.models.GuestStockResponse;
import java.util.List;

@RequestMapping("/api/guest/stock")
public interface GuestStockAPI {
    @GetMapping("/product/{productId}")
    ResponseEntity<PageResponse<GuestStockResponse>> findAlByProductId(
            @PathVariable String productId,
            @RequestParam(required = false, value = "search", defaultValue = "") String search,
            @RequestParam(required = false, value = "colorIds", defaultValue = "") List<String> colorIds,
            @RequestParam(required = false, value = "sizeIds", defaultValue = "") List<String> sizeIds,
            @RequestParam(required = false, value = "sort", defaultValue = "") String sort,
            @RequestParam(required = false, value = "currentPage", defaultValue = "1") @Min(1) Integer currentPage,
            @RequestParam(required = false, value = "pageSize", defaultValue = "20") Integer pageSize
    );

    @GetMapping
    ResponseEntity<GuestStockResponse> findByProductIdSizeIdColorId(
            @RequestParam(required = true, value = "productId", defaultValue = "") String productId,
            @RequestParam(required = true, value = "colorId", defaultValue = "") String colorId,
            @RequestParam(required = true, value = "sizeId", defaultValue = "") String sizeId
    );

    @GetMapping("/{stockId}")
    ResponseEntity<GuestStockResponse> findById(@PathVariable String stockId);
}

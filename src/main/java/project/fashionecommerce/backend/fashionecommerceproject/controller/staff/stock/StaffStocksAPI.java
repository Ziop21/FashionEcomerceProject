package project.fashionecommerce.backend.fashionecommerceproject.controller.staff.stock;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.fashionecommerce.backend.fashionecommerceproject.common.models.PageResponse;
import project.fashionecommerce.backend.fashionecommerceproject.controller.staff.stock.models.StaffStockRequest;
import project.fashionecommerce.backend.fashionecommerceproject.controller.staff.stock.models.StaffStockResponse;
import java.util.List;

@RequestMapping("/api/staff/stock")
public interface StaffStocksAPI {
    @PostMapping
    void save(@RequestBody @Valid StaffStockRequest stockRequest);

    @GetMapping
    ResponseEntity<PageResponse<StaffStockResponse>> findAll(
            @RequestParam(required = false, value = "search", defaultValue = "") String search,
            @RequestParam(required = false, value = "sizeIds", defaultValue = "") List<String> sizeIds,
            @RequestParam(required = false, value = "colorIds", defaultValue = "") List<String> colorIds,
            @RequestParam(required = false, value = "sort", defaultValue = "") String sort,
            @RequestParam(required = false, value = "pageCurrent", defaultValue = "1") @Min(1) Integer pageCurrent,
            @RequestParam(required = false, value = "pageSize", defaultValue = "20") Integer pageSize
    );
}

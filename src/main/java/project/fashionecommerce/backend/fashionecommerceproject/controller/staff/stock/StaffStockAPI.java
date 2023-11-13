package project.fashionecommerce.backend.fashionecommerceproject.controller.staff.stock;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.fashionecommerce.backend.fashionecommerceproject.controller.staff.stock.models.StaffStockRequest;
import project.fashionecommerce.backend.fashionecommerceproject.controller.staff.stock.models.StaffStockResponse;

@RequestMapping("/api/staff/stock/{stockId}")
public interface StaffStockAPI {
    @PutMapping
    void update(@PathVariable String stockId, @RequestBody @Valid StaffStockRequest staffStockRequest);

    @GetMapping
    ResponseEntity<StaffStockResponse> findById(@PathVariable String stockId);

    @DeleteMapping
    void delete(@PathVariable String stockId);
}

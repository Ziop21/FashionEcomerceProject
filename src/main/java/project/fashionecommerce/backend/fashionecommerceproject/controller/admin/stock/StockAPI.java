package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.stock;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.stock.models.StockRequest;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.stock.models.StockResponse;

@RequestMapping("/api/admin/stock/{stockId}")
public interface StockAPI {
    @PutMapping
    void update(@PathVariable String stockId, @RequestBody @Valid StockRequest stockRequest);

    @GetMapping
    ResponseEntity<StockResponse> findById(@PathVariable String stockId);

    @DeleteMapping
    void delete(@PathVariable String stockId);
}

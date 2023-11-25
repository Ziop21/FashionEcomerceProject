package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.order;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.order.models.OrderRequest;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.order.models.OrderResponse;

@RequestMapping("/api/admin/manager/order/{orderId}")
public interface OrderAPI {
    @PutMapping
    void update(@PathVariable String orderId, @RequestBody @Valid OrderRequest orderRequest);

    @GetMapping
    ResponseEntity<OrderResponse> findById(@PathVariable String orderId);

    @DeleteMapping
    void delete(@PathVariable String orderId);
    
}

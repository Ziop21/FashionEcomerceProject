package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.delivery;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.delivery.models.DeliveryRequest;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.delivery.models.DeliveryResponse;

@RequestMapping("/api/admin/manager/delivery/{deliveryId}")
public interface DeliveryAPI {
    @GetMapping
    ResponseEntity<DeliveryResponse> findById(@PathVariable String deliveryId);
    @DeleteMapping
    void delete(@PathVariable String deliveryId);
    @PutMapping
    void update(@PathVariable String deliveryId, @RequestBody @Valid DeliveryRequest deliveryRequest);
}

package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.delivery;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.delivery.models.DeliveryModelMapper;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.delivery.models.DeliveryRequest;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.delivery.models.DeliveryResponse;
import project.fashionecommerce.backend.fashionecommerceproject.dto.delivery.Delivery;
import project.fashionecommerce.backend.fashionecommerceproject.dto.delivery.DeliveryId;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.delivery.DeliveryUseCaseService;

@RestController
@RequiredArgsConstructor
public class DeliveryController implements DeliveryAPI{
    @NonNull final DeliveryUseCaseService deliveryUseCaseService;
    @NonNull final DeliveryModelMapper deliveryModelMapper;
    @Override
    public ResponseEntity<DeliveryResponse> findById(String deliveryId) {
        Delivery delivery = deliveryUseCaseService.findById(new DeliveryId(deliveryId));
        return new ResponseEntity<>(deliveryModelMapper.toModel(delivery), HttpStatus.OK);
    }

    @Override
    public void delete(String deliveryId) {
        deliveryUseCaseService.delete(new DeliveryId(deliveryId));
    }

    @Override
    public void update(String deliveryId, DeliveryRequest deliveryRequest) {
        Delivery delivery = deliveryModelMapper.toDto(deliveryRequest);
        deliveryUseCaseService.update(new DeliveryId(deliveryId), delivery);
    }
}

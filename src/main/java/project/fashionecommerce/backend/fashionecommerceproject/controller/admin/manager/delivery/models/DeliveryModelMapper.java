package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.delivery.models;

import org.mapstruct.Mapper;
import project.fashionecommerce.backend.fashionecommerceproject.dto.delivery.Delivery;

@Mapper(componentModel = "spring")
public interface DeliveryModelMapper {
    Delivery toDto(DeliveryRequest deliveryRequest);

    DeliveryResponse toModel(Delivery delivery);
}

package project.fashionecommerce.backend.fashionecommerceproject.controller.guest.order.models;

import org.mapstruct.Mapper;
import project.fashionecommerce.backend.fashionecommerceproject.dto.order.Order;

@Mapper(componentModel = "spring")
public interface GuestOrderModelMapper {
    Order toDto(GuestOrderRequest guestOrderRequest);
}

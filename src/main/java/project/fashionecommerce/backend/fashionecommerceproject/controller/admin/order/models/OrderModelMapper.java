package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.order.models;

import org.mapstruct.Mapper;
import project.fashionecommerce.backend.fashionecommerceproject.dto.order.Order;

@Mapper(componentModel = "spring")
public interface OrderModelMapper {
    Order toDto(OrderRequest orderRequest);

    OrderResponse toModel(Order order);
}

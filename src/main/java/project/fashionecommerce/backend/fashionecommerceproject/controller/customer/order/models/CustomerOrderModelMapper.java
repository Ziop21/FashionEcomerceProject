package project.fashionecommerce.backend.fashionecommerceproject.controller.customer.order.models;

import org.mapstruct.Mapper;
import project.fashionecommerce.backend.fashionecommerceproject.dto.order.Order;

@Mapper(componentModel = "spring")
public interface CustomerOrderModelMapper {
    CustomerOrderResponse toModel(Order order);
}

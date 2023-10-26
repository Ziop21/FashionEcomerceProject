package project.fashionecommerce.backend.fashionecommerceproject.dto.order;

import org.mapstruct.Mapper;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.order.OrderEntity;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderEntity toEntity(Order order);

    Order toDto(OrderEntity orderEntity);
}

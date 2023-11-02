package project.fashionecommerce.backend.fashionecommerceproject.dto.order;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import project.fashionecommerce.backend.fashionecommerceproject.dto.enums.EOrderStatus;
import project.fashionecommerce.backend.fashionecommerceproject.dto.order.items.OrderItem;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.order.OrderEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderEntity toEntity(Order order);

    Order toDto(OrderEntity orderEntity);
    @Mapping(source = "id", target = "id", ignore = true)
    @Mapping(source = "createdAt", target = "createdAt", ignore = true)
    void updateExisted(Order order, @MappingTarget OrderEntity foundEntity);

    @Mapping(source = "status", target = "status")
    @Mapping(source = "orderItems", target = "orderItems")
    Order updateDto(Order order, EOrderStatus status, List<OrderItem> orderItems);

    @Mapping(source = "userId", target = "userId")
    Order updateDtoUserId(Order order, String userId);
}

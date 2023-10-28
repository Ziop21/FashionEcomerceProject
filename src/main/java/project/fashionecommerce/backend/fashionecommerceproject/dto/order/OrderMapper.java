package project.fashionecommerce.backend.fashionecommerceproject.dto.order;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.order.OrderEntity;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderEntity toEntity(Order order);

    Order toDto(OrderEntity orderEntity);
    @Mapping(source = "id", target = "id", ignore = true)
    @Mapping(source = "createdAt", target = "createdAt", ignore = true)
    void updateExisted(Order order, @MappingTarget OrderEntity foundEntity);
}

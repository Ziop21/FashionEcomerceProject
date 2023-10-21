package project.fashionecommerce.backend.fashionecommerceproject.dto.delivery;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.delivery.DeliveryEntity;

@Mapper(componentModel = "spring")
public interface DeliveryMapper {
    DeliveryEntity toEntity(Delivery delivery);

    Delivery toDto(DeliveryEntity deliveryEntity);

    @Mapping(source = "id", target = "id", ignore = true)
    @Mapping(source = "createdBy", target = "createdBy", ignore = true)
    @Mapping(source = "createdAt", target = "createdAt", ignore = true)
    void updateExisted(Delivery delivery, @MappingTarget DeliveryEntity deliveryEntity);
}

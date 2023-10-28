package project.fashionecommerce.backend.fashionecommerceproject.dto.cart;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.cart.CartEntity;

@Mapper(componentModel = "spring")
public interface CartMapper {
    CartEntity toEntity(Cart cart);

    Cart toDto(CartEntity cartEntity);
    @Mapping(source = "id", target = "id", ignore = true)
    @Mapping(source = "createdAt", target = "createdAt", ignore = true)
    void updateExist(Cart cart, @MappingTarget CartEntity foundEntity);
}

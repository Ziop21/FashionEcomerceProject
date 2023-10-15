package project.fashionecommerce.backend.fashionecommerceproject.dto.cart;

import org.mapstruct.Mapper;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.cart.CartEntity;

@Mapper(componentModel = "spring")
public interface CartMapper {
    CartEntity toEntity(Cart cart);

    Cart toDto(CartEntity cartEntity);
}

package project.fashionecommerce.backend.fashionecommerceproject.dto.cart;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import project.fashionecommerce.backend.fashionecommerceproject.dto.cart.items.CartItem;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.cart.CartEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CartMapper {
    CartEntity toEntity(Cart cart);

    Cart toDto(CartEntity cartEntity);

    @Mapping(source = "id", target = "id", ignore = true)
    @Mapping(source = "createdAt", target = "createdAt", ignore = true)
    void updateExist(Cart cart, @MappingTarget CartEntity foundEntity);

    @Mapping(source = "cartItems", target = "cartItems")
    Cart updateCartItems(Cart cart, List<CartItem> cartItems);
}

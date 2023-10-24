package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.cart.models;

import org.mapstruct.Mapper;
import project.fashionecommerce.backend.fashionecommerceproject.dto.cart.Cart;

@Mapper(componentModel = "spring")
public interface CartModelMapper {
    Cart toDto(CartRequest cartRequest);

    CartResponse toModel(Cart cart);
}

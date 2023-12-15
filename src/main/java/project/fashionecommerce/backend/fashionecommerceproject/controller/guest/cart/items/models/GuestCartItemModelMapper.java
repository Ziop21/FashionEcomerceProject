package project.fashionecommerce.backend.fashionecommerceproject.controller.guest.cart.items.models;

import org.mapstruct.Mapper;
import project.fashionecommerce.backend.fashionecommerceproject.dto.cart.items.CartItem;

@Mapper(componentModel = "spring")
public interface GuestCartItemModelMapper {
    GuestCartItemResponse toModel(CartItem cartItem);
    CartItem toDto(GuestCartItemRequest guestCartItemRequest);
}

package project.fashionecommerce.backend.fashionecommerceproject.controller.guest.cart.models;

import project.fashionecommerce.backend.fashionecommerceproject.controller.guest.cart.items.models.GuestCartItemRequest;

import java.util.List;

public record GuestCartRequest(
        String cartToken,
        List<GuestCartItemRequest> cartItems
) {
}

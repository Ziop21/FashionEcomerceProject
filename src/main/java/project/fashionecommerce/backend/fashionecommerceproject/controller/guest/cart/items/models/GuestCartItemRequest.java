package project.fashionecommerce.backend.fashionecommerceproject.controller.guest.cart.items.models;

public record GuestCartItemRequest(
        String stockId,
        Long quantity
) {
}

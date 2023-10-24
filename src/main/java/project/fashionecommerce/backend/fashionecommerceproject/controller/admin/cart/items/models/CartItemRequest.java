package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.cart.items.models;

public record CartItemRequest(
        String stockId,
        Long quantity
) {
}

package project.fashionecommerce.backend.fashionecommerceproject.controller.guest.cart.items.models;

public record GuestCartItemResponse(
        String stockId,
        Long quantity,
        String productName,
        String sizeName,
        String colorName,
        Double price,
        Double promotionalPrice
) {
}

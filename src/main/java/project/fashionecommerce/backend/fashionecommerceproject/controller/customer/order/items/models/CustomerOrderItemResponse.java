package project.fashionecommerce.backend.fashionecommerceproject.controller.customer.order.items.models;

public record CustomerOrderItemResponse(
        Long quantity,
        String stockId,
        String productId,
        String productName,
        Double price,
        String sizeId,
        String sizeName,
        String colorId,
        String colorName
) {
}

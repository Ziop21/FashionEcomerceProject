package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.order.items.models;

public record OrderItemRequest (
        Long quantity,
        String stockId,
        Boolean isDeleted,
        Boolean isActive
) {
}

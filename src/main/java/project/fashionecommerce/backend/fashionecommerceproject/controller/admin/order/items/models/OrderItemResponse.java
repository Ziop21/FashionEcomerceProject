package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.order.items.models;

import java.time.LocalDateTime;

public record OrderItemResponse (
        Long quantity,
        String stockId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Boolean isDeleted,
        Boolean isActive
) {
}

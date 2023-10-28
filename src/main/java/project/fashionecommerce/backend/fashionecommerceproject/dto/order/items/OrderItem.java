package project.fashionecommerce.backend.fashionecommerceproject.dto.order.items;

import java.time.LocalDateTime;

public record OrderItem(
        Long quantity,
        String stockId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Boolean isDeleted,
        Boolean isActive
) {
}

package project.fashionecommerce.backend.fashionecommerceproject.dto.order.items;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record OrderItem(
        Long quantity,
        String stockId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Boolean isDeleted,
        Boolean isActive
) {
}

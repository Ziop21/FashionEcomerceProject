package project.fashionecommerce.backend.fashionecommerceproject.dto.delivery;

import java.time.LocalDateTime;

public record Delivery (
        String id,
        String name,
        String description,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        String createdBy,
        String updatedBy,
        Boolean isDeleted,
        Boolean isActive
) {
}

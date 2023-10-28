package project.fashionecommerce.backend.fashionecommerceproject.dto.color;

import java.time.LocalDateTime;

public record Color(
        String id,
        String name,
        String updatedBy,
        String createdBy,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Boolean isDeleted,
        Boolean isActive
) {
}

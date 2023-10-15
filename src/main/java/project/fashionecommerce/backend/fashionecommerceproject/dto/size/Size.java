package project.fashionecommerce.backend.fashionecommerceproject.dto.size;

import java.time.LocalDateTime;

public record Size (
        String id,
        String name,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        String createdBy,
        String updatedBy
) {
}

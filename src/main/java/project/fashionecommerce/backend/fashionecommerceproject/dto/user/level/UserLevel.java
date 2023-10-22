package project.fashionecommerce.backend.fashionecommerceproject.dto.user.level;

import java.time.LocalDateTime;

public record UserLevel (
        String id,
        String name,
        String description,
        Long minPoint,
        Double discount,
        Boolean isDeleted,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        String createdBy,
        String updatedBy
) {
}

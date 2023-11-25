package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.user.level.models;

import java.time.LocalDateTime;

public record UserLevelResponse(
        String id,
        String name,
        String description,
        Long minPoint,
        Double discount,
        Boolean isDeleted,
        Boolean isActive,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        String createdBy,
        String updatedBy
) {
}

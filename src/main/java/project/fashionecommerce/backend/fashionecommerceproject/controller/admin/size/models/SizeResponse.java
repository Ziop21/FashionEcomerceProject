package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.size.models;

import java.time.LocalDateTime;

public record SizeResponse(
        String id,
        String name,
        Boolean isDeleted,
        Boolean isActive,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        String createdBy,
        String updatedBy
) {
}

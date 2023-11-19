package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.category.models;

import java.time.LocalDateTime;
import java.util.List;

public record CategoryResponse(
        String id,
        List<String> categoryIds,
        String name,
        String slug,
        List<String> images,
        String updatedBy,
        String createdBy,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Boolean isDeleted,
        Boolean isActive
) {
}

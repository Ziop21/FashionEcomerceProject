package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.category.models;

import java.time.LocalDateTime;
import java.util.List;

public record CategoryResponse(
        String id,
        List<String> categoryIds,
        String name,
        String slug,
        String image,
        String updatedBy,
        String createdBy,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}

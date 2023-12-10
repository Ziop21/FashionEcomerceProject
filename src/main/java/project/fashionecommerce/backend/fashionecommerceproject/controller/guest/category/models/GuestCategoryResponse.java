package project.fashionecommerce.backend.fashionecommerceproject.controller.guest.category.models;

import java.time.LocalDateTime;
import java.util.List;

public record GuestCategoryResponse(
        String id,
        List<String> categoryIds,
        String name,
        String slug,
        List<String> images,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}

package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.review.models;

import java.time.LocalDateTime;
import java.util.List;

public record ReviewResponse(
        String orderId,
        String content,
        Double rating,
        List<String> images,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Boolean isDeleted,
        Boolean isActive
) {
}

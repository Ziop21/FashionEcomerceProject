package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.review.models;

import java.time.LocalDateTime;
import java.util.List;

public record ReviewResponse(
        String userId,
        String content,
        Double rating,
        List<String> images,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}

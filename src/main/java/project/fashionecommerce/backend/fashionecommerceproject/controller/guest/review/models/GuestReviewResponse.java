package project.fashionecommerce.backend.fashionecommerceproject.controller.guest.review.models;

import java.time.LocalDateTime;
import java.util.List;

public record GuestReviewResponse(
        String sizeName,
        String colorName,
        String orderId,
        String userId,
        String username,
        String content,
        Double rating,
        List<String> images,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}

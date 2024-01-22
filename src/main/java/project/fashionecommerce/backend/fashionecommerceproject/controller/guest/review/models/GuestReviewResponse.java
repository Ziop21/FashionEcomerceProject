package project.fashionecommerce.backend.fashionecommerceproject.controller.guest.review.models;

import java.time.LocalDateTime;
import java.util.List;

public record GuestReviewResponse(
        String userId,
        String username,
        String orderId,
        String content,
        Long rating,
        List<String> images,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}

package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.follow.models;

import java.time.LocalDateTime;

public record FollowResponse(
        String id,
        String productId,
        String userId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Boolean isDeleted
) {
}

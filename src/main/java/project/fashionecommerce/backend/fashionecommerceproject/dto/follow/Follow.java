package project.fashionecommerce.backend.fashionecommerceproject.dto.follow;

import java.time.LocalDateTime;

public record Follow (
        String id,
        String productId,
        String userId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Boolean isDeleted
) {
}

package project.fashionecommerce.backend.fashionecommerceproject.dto.review;

import java.time.LocalDateTime;
import java.util.List;

public record Review(
        String orderId,
        String userId,
        String username,
        String content,
        Double rating,
        List<String> images,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Boolean isDeleted,
        Boolean isActive
) {
}

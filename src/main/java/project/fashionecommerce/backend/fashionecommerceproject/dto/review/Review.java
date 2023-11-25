package project.fashionecommerce.backend.fashionecommerceproject.dto.review;

import java.time.LocalDateTime;
import java.util.List;

public record Review(
        String userId,
        String content,
        Double rating,
        List<String> images,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Boolean isDeleted,
        Boolean isActive,
        String username
) {
}

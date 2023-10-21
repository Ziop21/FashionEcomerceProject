package project.fashionecommerce.backend.fashionecommerceproject.dto.category;

import java.time.LocalDateTime;
import java.util.List;

public record Category (
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

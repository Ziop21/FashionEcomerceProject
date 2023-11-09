package project.fashionecommerce.backend.fashionecommerceproject.dto.category;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record Category (
        String id,
        List<String> categoryIds,
        String name,
        String slug,
        List<String> images,
        String updatedBy,
        String createdBy,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Boolean isActive,
        Boolean isDeleted
) {
}

package project.fashionecomerce.backend.fashionecomerceproject.dto.size;

import java.time.LocalDateTime;

public record Size (
        String id,
        String name,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime createdBy,
        LocalDateTime updatedBy

) {
}

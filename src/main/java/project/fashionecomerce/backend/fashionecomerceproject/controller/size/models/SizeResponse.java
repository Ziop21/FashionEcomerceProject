package project.fashionecomerce.backend.fashionecomerceproject.controller.size.models;

import java.time.LocalDateTime;

public record SizeResponse(
        String id,
        String name,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime createdBy,
        LocalDateTime updatedBy
) {
}

package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.color.models;

import java.time.LocalDateTime;

public record ColorResponse(
        String id,
        String name,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        String createdBy,
        String updatedBy
) { }

package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.product.models;

import java.time.LocalDateTime;
import java.util.List;

public record ProductResponse(
        String id,
        String name,
        String slug,
        String description,
        Double price,
        Double promotionalPrice,
        Long view,
        Boolean isSelling,
        List<String> images,
        Double rating,
        Boolean isDeleted,
        Boolean isActive,
        String createdBy,
        String updatedBy,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}

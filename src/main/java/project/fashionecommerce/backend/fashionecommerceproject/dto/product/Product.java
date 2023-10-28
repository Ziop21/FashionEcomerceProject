package project.fashionecommerce.backend.fashionecommerceproject.dto.product;

import java.time.LocalDateTime;
import java.util.List;

public record Product (
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
        String createdBy,
        String updatedBy,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Boolean isDeleted,
        Boolean isActive
) {
}

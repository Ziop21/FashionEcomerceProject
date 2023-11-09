package project.fashionecommerce.backend.fashionecommerceproject.controller.staff.product.models;

import java.time.LocalDateTime;
import java.util.List;

public record StaffProductResponse(
        String id,
        String name,
        String description,
        Double price,
        Double promotionalPrice,
        Boolean isSelling,
        List<String> images,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        String createdBy,
        String updatedBy
) {
}

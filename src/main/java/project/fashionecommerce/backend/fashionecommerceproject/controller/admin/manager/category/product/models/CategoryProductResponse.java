package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.category.product.models;

import java.time.LocalDateTime;

public record CategoryProductResponse(
        String id,
        String categoryId,
        String productId,
        Boolean isDeleted,
        Boolean isActive,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        String createdBy,
        String updatedBy
) {
}

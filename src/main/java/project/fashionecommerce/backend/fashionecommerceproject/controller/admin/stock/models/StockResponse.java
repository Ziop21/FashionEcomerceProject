package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.stock.models;

import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.review.models.ReviewResponse;
import java.time.LocalDateTime;
import java.util.List;

public record StockResponse(
        String id,
        String productId,
        String sizeId,
        String colorId,
        Long quantity,
        Boolean isActive,
        Boolean isDeleted,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        String createdBy,
        String updatedBy,
        List<ReviewResponse> reviews
) {
}

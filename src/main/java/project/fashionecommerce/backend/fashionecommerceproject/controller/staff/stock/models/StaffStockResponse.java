package project.fashionecommerce.backend.fashionecommerceproject.controller.staff.stock.models;

import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.review.models.ReviewResponse;

import java.time.LocalDateTime;
import java.util.List;

public record StaffStockResponse(
        String id,
        String productId,
        String sizeId,
        String colorId,
        Long quantity,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        String createdBy,
        String updatedBy
) {
}

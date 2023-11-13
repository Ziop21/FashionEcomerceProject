package project.fashionecommerce.backend.fashionecommerceproject.controller.staff.stock.models;

import java.time.LocalDateTime;

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

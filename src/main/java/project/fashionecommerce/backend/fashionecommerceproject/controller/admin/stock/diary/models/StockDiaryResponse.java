package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.stock.diary.models;

import java.time.LocalDateTime;

public record StockDiaryResponse(
        String id,
        String stockId,
        Long quantity,
        Long errorQuantity,
        String note,
        String updatedBy,
        String createdBy,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}

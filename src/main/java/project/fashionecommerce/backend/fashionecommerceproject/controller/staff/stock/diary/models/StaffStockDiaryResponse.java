package project.fashionecommerce.backend.fashionecommerceproject.controller.staff.stock.diary.models;

import java.time.LocalDateTime;

public record StaffStockDiaryResponse(
        String id,
        String stockId,
        Long quantity,
        Long errorQuantity,
        String note,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        String createdBy,
        String updatedBy
) {
}

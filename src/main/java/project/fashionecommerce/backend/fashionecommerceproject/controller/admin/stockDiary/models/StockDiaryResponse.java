package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.stockDiary.models;
import java.time.LocalDateTime;

public record StockDiaryResponse(
        String id,
        String stockId,
        long quantity,
        long errorQuantity,
        String note,
        String updatedBy,
        String createdBy,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}

package project.fashionecommerce.backend.fashionecommerceproject.dto.stockDiary;

import org.bson.types.ObjectId;

import java.time.LocalDateTime;

public record StockDiary(
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

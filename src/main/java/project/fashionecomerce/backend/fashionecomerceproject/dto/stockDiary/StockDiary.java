package project.fashionecomerce.backend.fashionecomerceproject.dto.stockDiary;

import java.util.Date;

public record StockDiary(
        String id,
        String stockId,
        Long quantity,
        Long errorQuantity,
        String note,
        Date createdAt,
        Date updatedAt,
        String createdBy,
        String updatedBy,
        Boolean isDeleted,
        Boolean isActive
) {
}

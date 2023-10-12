package project.fashionecomerce.backend.fashionecomerceproject.controller.stockDiary.models;
import java.util.Date;
public record StockDiaryResponse(
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

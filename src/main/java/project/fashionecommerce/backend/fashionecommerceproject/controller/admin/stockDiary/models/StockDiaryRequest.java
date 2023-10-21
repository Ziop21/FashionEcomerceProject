package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.stockDiary.models;

import org.bson.types.ObjectId;

public record StockDiaryRequest(
        String stockId,
        long quantity,
        long errorQuantity,
        String note
) {
}

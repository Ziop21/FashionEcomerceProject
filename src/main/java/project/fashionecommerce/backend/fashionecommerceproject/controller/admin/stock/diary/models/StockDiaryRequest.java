package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.stock.diary.models;

public record StockDiaryRequest(
        String stockId,
        long quantity,
        long errorQuantity,
        String note
) {
}

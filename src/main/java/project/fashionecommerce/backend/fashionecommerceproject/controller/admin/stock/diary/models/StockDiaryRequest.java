package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.stock.diary.models;

import jakarta.validation.constraints.NotNull;

public record StockDiaryRequest(
        @NotNull
        String stockId,
        @NotNull
        Long quantity,
        Long errorQuantity,
        String note
) {
}

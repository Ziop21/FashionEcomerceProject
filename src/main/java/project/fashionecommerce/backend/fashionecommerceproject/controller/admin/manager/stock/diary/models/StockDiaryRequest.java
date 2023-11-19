package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.stock.diary.models;

import jakarta.validation.constraints.NotNull;

public record StockDiaryRequest(
        @NotNull
        String stockId,
        @NotNull
        Long quantity,
        @NotNull
        Long errorQuantity,
        String note,
        @NotNull
        Boolean isDeleted,

        @NotNull
        Boolean isActive
) {
}

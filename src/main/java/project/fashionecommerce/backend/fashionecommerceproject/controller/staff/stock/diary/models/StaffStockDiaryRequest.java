package project.fashionecommerce.backend.fashionecommerceproject.controller.staff.stock.diary.models;

import jakarta.validation.constraints.NotNull;

public record StaffStockDiaryRequest(
        @NotNull
        String stockId,
        @NotNull
        Long quantity,
        @NotNull
        Long errorQuantity,
        String note
) {
}

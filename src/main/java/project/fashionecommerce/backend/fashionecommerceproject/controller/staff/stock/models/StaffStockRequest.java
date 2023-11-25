package project.fashionecommerce.backend.fashionecommerceproject.controller.staff.stock.models;

import jakarta.validation.constraints.NotNull;

public record StaffStockRequest (
        @NotNull
        String productId,
        @NotNull
        String sizeId,
        @NotNull
        String colorId,
        @NotNull
        Long quantity
) {
}

package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.stock.models;

import jakarta.validation.constraints.NotNull;


public record StockRequest(
        @NotNull
        String productId,
        @NotNull
        String sizeId,
        @NotNull
        String colorId,
        @NotNull
        Long quantity,
        Boolean isActive,
        Boolean isDeleted
) {
}

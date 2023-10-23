package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.stock.models;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record StockRequest(
        @NotNull
        String productId,
        @NotNull
        String sizeId,
        @NotNull
        List<String> colorIds,
        @NotNull
        Long quantity,
        @NotNull
        Boolean isMixedColor,
        Boolean isActive,
        Boolean isDeleted
) {
}

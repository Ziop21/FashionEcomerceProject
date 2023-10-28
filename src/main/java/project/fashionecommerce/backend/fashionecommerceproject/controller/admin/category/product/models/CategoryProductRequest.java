package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.category.product.models;

import jakarta.validation.constraints.NotNull;

public record CategoryProductRequest(
        @NotNull
        String categoryId,
        @NotNull
        String productId,
        @NotNull
        Boolean isDeleted,
        @NotNull
        Boolean isActive
) {
}

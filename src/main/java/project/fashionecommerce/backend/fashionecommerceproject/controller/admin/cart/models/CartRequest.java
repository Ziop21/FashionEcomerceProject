package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.cart.models;

import jakarta.validation.constraints.NotNull;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.cart.items.models.CartItemRequest;

import java.util.List;

public record CartRequest(
        List<CartItemRequest> cartItems,

        @NotNull
        Boolean isDeleted,

        @NotNull
        Boolean isActive
) {
}

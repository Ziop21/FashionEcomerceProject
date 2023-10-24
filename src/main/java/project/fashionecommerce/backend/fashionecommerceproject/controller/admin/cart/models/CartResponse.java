package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.cart.models;

import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.cart.items.models.CartItemRequest;

import java.time.LocalDateTime;
import java.util.List;

public record CartResponse(
        String id,
        String userId,
        Boolean isDeleted,
        List<CartItemRequest> cartItems,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}

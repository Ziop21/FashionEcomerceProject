package project.fashionecommerce.backend.fashionecommerceproject.dto.cart;

import lombok.Builder;
import project.fashionecommerce.backend.fashionecommerceproject.dto.cart.items.CartItem;
import java.time.LocalDateTime;
import java.util.List;

@Builder
public record Cart(
        String id,

        List<CartItem> cartItems,
        Boolean isDeleted,
        Boolean isActive,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}

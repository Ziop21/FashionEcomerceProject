package project.fashionecommerce.backend.fashionecommerceproject.dto.cart.items;

import java.time.LocalDateTime;

public record CartItem (
        String stockId,
        Long quantity,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
){
}

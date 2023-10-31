package project.fashionecommerce.backend.fashionecommerceproject.dto.cart.items;

import lombok.Builder;

import java.time.LocalDateTime;
@Builder
public record CartItem (
        String stockId,
        Long quantity,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Boolean isDeleted,
        Boolean isActive,
        String productName,
        String sizeName,
        String colorName,
        Double price,
        Double promotionalPrice
){
}

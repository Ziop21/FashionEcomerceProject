package project.fashionecommerce.backend.fashionecommerceproject.dto.follow;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record Follow (
        String id,
        String productId,
        String userId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Boolean isDeleted,
        Boolean isActive,
        String productName,
        Double price,
        Double promotionalPrice,
        String image
) {
}

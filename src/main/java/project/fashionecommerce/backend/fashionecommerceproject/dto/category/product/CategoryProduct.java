package project.fashionecommerce.backend.fashionecommerceproject.dto.category.product;

import lombok.Builder;
import java.time.LocalDateTime;

@Builder
public record CategoryProduct(
        String id,
        String categoryId,
        String productId,
        Boolean isDeleted,
        Boolean isActive,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        String createdBy,
        String updatedBy
) {
}

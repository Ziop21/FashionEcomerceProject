package project.fashionecommerce.backend.fashionecommerceproject.dto.stock;

import lombok.Builder;
import project.fashionecommerce.backend.fashionecommerceproject.dto.review.Review;
import java.time.LocalDateTime;
import java.util.List;
@Builder
public record Stock(
        String id,
        String productId,
        String sizeId,
        List<String> colorIds,
        Long quantity,
        Boolean isMixedColor,
        Boolean isActive,
        Boolean isDeleted,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        String createdBy,
        String updatedBy,
        List<Review> reviews
) {
}

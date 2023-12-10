package project.fashionecommerce.backend.fashionecommerceproject.controller.guest.category.product.models;

import java.time.LocalDateTime;

public record GuestCategoryProductModel(
     String id,
     String productId,
     String categoryId,
     LocalDateTime createdAt,
     LocalDateTime updatedAt
) {
}

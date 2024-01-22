package project.fashionecommerce.backend.fashionecommerceproject.controller.guest.stock.models;

import project.fashionecommerce.backend.fashionecommerceproject.controller.guest.review.models.GuestReviewResponse;
import java.util.List;

public record GuestStockResponse(
        String id,
        String productId,
        String sizeId,
        String colorId,
        Long quantity,
        List<GuestReviewResponse> reviews
) {
}

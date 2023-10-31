package project.fashionecommerce.backend.fashionecommerceproject.controller.guest.stock.models;

import project.fashionecommerce.backend.fashionecommerceproject.controller.guest.color.models.GuestColorResponse;
import project.fashionecommerce.backend.fashionecommerceproject.controller.guest.review.models.GuestReviewResponse;
import project.fashionecommerce.backend.fashionecommerceproject.controller.guest.size.models.GuestSizeResponse;
import java.util.List;

public record GuestStockResponse(
        String id,
        GuestSizeResponse size,
        GuestColorResponse color,
        Long quantity,
        List<GuestReviewResponse> reviews
) {
}

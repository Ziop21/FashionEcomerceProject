package project.fashionecommerce.backend.fashionecommerceproject.controller.guest.product.models;

import lombok.Builder;
import project.fashionecommerce.backend.fashionecommerceproject.controller.guest.color.models.GuestColorResponse;
import project.fashionecommerce.backend.fashionecommerceproject.controller.guest.review.models.GuestReviewResponse;
import project.fashionecommerce.backend.fashionecommerceproject.controller.guest.size.models.GuestSizeResponse;
import project.fashionecommerce.backend.fashionecommerceproject.controller.guest.stock.models.GuestStockResponse;

import java.util.List;

@Builder
public record GuestProductResponse(
        String productId,
        String name,
        String description,
        Double price,
        Double promotionalPrice,
        List<String> images,
        Double rating,
        List<GuestStockResponse> stocks
) {
}

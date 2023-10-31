package project.fashionecommerce.backend.fashionecommerceproject.controller.guest.review.models;

import java.util.List;

public record GuestReviewResponse(
        String sizeName,
        String colorName,
        String username,
        String content,
        Double rating,
        List<String> images
) {
}

package project.fashionecommerce.backend.fashionecommerceproject.controller.customer.review.models;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CustomerReviewRequest(
        @NotNull
        String orderId,
        String content,
        @NotNull
        Long rating,
        List<String> images
) {
}

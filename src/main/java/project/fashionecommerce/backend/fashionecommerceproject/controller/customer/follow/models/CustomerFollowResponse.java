package project.fashionecommerce.backend.fashionecommerceproject.controller.customer.follow.models;

public record CustomerFollowResponse(
        String id,
        String productId,
        String productName,
        Double price,
        Double promotionalPrice,
        String image
) {
}

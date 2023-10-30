package project.fashionecommerce.backend.fashionecommerceproject.controller.guest.product.models;

import java.util.List;

public record GuestProductResponse(
        String productId,
        String name,
        String description,
        Double price,
        Double promotionalPrice,
        List<String> images,
        Double rating
) {
}

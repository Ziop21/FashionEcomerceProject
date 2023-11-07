package project.fashionecommerce.backend.fashionecommerceproject.controller.staff.product.models;

import java.util.List;

public record StaffProductRequest(
        String name,
        String description,
        Double price,
        Double promotionalPrice,
        Boolean isSelling,
        List<String> images
) {
}

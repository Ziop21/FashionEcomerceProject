package project.fashionecommerce.backend.fashionecommerceproject.controller.staff.product.models;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record StaffProductRequest(
        @NotNull
        String name,
        String description,
        @NotNull
        Double price,
        Double promotionalPrice,
        List<String> images
) {
}

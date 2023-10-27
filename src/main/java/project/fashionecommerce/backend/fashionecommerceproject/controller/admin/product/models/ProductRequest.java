package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.product.models;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public record ProductRequest(
        @NotNull
        String name,
        String slug,
        @NotNull
        String description,
        @NotNull
        Double price,
        Double promotionalPrice,
        Long view,
        @NotNull
        Boolean isSelling,
        List<String> images,
        Double rating,
        @NotNull
        Boolean isActive
) {
}

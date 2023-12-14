package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.product.models;

import jakarta.validation.constraints.Min;
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
        @Min(0)
        Double promotionalPrice,
        Long view,
        @NotNull
        Boolean isSelling,
        @NotNull
        List<String> images,
        Double rating,
        @NotNull
        Boolean isDeleted,
        @NotNull
        Boolean isActive
) {
}

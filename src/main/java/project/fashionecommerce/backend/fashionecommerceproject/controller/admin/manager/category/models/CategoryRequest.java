package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.category.models;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CategoryRequest(
        List<String> categoryIds,
        @NotNull
        String name,
        String slug,
        List<String> images,
        Boolean isDeleted,
        Boolean isActive
) {
}

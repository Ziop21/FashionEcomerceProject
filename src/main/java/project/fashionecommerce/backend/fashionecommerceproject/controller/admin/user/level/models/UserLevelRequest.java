package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.user.level.models;

import jakarta.validation.constraints.NotNull;

public record UserLevelRequest(
        @NotNull
        String name,
        String description,
        @NotNull
        Long minPoint,
        @NotNull
        Double discount,
        Boolean isDeleted,
        Boolean isActive
) {
}

package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.size.models;

import jakarta.validation.constraints.NotNull;

public record SizeRequest(
        @NotNull
        String name,
        Boolean isDeleted,
        Boolean isActive
) {
}

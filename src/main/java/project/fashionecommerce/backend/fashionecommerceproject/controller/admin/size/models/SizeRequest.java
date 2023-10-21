package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.size.models;

import jakarta.validation.constraints.NotNull;

public record SizeRequest(
        @NotNull
        String name
) {
}

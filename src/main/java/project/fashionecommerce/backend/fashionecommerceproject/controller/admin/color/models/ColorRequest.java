package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.color.models;

import jakarta.validation.constraints.NotNull;

public record ColorRequest(
        @NotNull
        String name
) { }

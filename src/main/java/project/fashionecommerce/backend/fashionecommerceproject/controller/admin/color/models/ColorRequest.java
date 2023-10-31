package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.color.models;

import jakarta.validation.constraints.NotNull;

public record ColorRequest(
        @NotNull
        String name,
        @NotNull
        Boolean isDeleted,
        @NotNull
        String code,
        @NotNull
        Boolean isActive
) { }

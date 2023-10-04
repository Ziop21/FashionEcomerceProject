package project.fashionecomerce.backend.fashionecomerceproject.controller.color.models;

import jakarta.validation.constraints.NotNull;

public record ColorRequest(
        @NotNull
        String name
) { }

package project.fashionecommerce.backend.fashionecommerceproject.controller.guest.authen.models;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank
        String email,
        @NotBlank
        String password
) {
}

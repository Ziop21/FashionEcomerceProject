package project.fashionecommerce.backend.fashionecommerceproject.controller.authentication.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record RegisterRequest(
        @NotNull
        @Email
        String email,
        @NotNull
        String password,
        @NotNull
        String confirmPassword
) {
}

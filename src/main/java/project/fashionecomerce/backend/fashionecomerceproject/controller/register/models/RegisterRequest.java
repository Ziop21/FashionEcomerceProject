package project.fashionecomerce.backend.fashionecomerceproject.controller.register.models;

import jakarta.validation.constraints.Email;

public record RegisterRequest(
        @Email
        String email,
        String password,
        String confirmPassword
) {
}

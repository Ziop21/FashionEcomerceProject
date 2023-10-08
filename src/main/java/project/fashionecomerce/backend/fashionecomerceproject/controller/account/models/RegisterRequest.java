package project.fashionecomerce.backend.fashionecomerceproject.controller.account.models;

import jakarta.validation.constraints.Email;

public record RegisterRequest(
        @Email
        String email,
        String password,
        String confirmPassword
) {
}

package project.fashionecommerce.backend.fashionecommerceproject.controller.customer.user.models;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record CustomerUserRequest(
        @NotBlank
        String confirmPassword,
        @NotBlank
        String firstName,
        @NotBlank
        String lastName,
        String idCard,
        List<String> phones,
        List<String> address,
        String avatar,
        String eWallet
) {
}

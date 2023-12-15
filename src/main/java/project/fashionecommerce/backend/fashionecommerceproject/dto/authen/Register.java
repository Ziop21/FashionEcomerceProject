package project.fashionecommerce.backend.fashionecommerceproject.dto.authen;

import jakarta.validation.constraints.NotNull;

public record Register (
    String email,
    String password,
    String confirmPassword,
    String firstName,
    String lastName,
    String phone,
    String address
) {
}

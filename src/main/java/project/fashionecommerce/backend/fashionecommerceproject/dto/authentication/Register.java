package project.fashionecommerce.backend.fashionecommerceproject.dto.authentication;

public record Register (
    String email,
    String password,
    String confirmPassword
) {
}

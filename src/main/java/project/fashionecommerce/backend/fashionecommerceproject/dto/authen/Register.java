package project.fashionecommerce.backend.fashionecommerceproject.dto.authen;

public record Register (
    String email,
    String password,
    String confirmPassword
) {
}

package project.fashionecommerce.backend.fashionecommerceproject.controller.customer.user.models;

public record CustomerPasswordRequest(
        String oldPassword,
        String newPassword,
        String confirmPassword
) {
}

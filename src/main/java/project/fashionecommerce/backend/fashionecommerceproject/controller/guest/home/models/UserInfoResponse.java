package project.fashionecommerce.backend.fashionecommerceproject.controller.guest.home.models;

public record UserInfoResponse(
        String jwt,
        String refreshJwt
) {
}

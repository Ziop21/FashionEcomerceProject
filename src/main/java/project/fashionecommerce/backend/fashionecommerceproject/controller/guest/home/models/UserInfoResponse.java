package project.fashionecommerce.backend.fashionecommerceproject.controller.guest.home.models;

import java.util.List;

public record UserInfoResponse(
        String id,
        String username,
        String email,
        List<String> role,
        String jwt

) {
}

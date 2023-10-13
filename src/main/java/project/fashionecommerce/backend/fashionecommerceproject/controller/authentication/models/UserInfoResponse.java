package project.fashionecommerce.backend.fashionecommerceproject.controller.authentication.models;

import java.util.List;

public record UserInfoResponse(
        String id,
        String username,
        String email,
        List<String> role

) {
}

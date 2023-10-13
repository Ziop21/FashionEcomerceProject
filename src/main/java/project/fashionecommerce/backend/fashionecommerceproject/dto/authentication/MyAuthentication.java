package project.fashionecommerce.backend.fashionecommerceproject.dto.authentication;

import project.fashionecommerce.backend.fashionecommerceproject.repository.security.user_detail.UserDetailsImpl;

public record MyAuthentication(
        UserDetailsImpl userDetails,
        String jwtCookieString,
        String jwtRefreshCookieString
) {
}

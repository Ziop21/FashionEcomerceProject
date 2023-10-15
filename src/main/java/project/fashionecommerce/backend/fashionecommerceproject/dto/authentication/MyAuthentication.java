package project.fashionecommerce.backend.fashionecommerceproject.dto.authentication;

import lombok.Builder;
import project.fashionecommerce.backend.fashionecommerceproject.config.security.userDetails.Implement.UserDetailsImpl;

@Builder
public record MyAuthentication(
        UserDetailsImpl userDetails,
        String cartTokenCookieString,
        String usernameCookieString,
        String jwtCookieString,
        String jwtRefreshCookieString
) {
}

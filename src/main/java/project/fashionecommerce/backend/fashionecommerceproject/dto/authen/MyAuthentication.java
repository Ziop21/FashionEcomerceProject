package project.fashionecommerce.backend.fashionecommerceproject.dto.authen;

import lombok.Builder;
import project.fashionecommerce.backend.fashionecommerceproject.config.security.userDetails.Implement.UserDetailsImpl;

@Builder
public record MyAuthentication(
        UserDetailsImpl userDetails,
        String cartTokenCookieString,
        String jwt,
        String jwtRefreshCookieString
) {
}

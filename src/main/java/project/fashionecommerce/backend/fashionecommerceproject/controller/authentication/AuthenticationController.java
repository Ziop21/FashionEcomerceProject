package project.fashionecommerce.backend.fashionecommerceproject.controller.authentication;

import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import project.fashionecommerce.backend.fashionecommerceproject.controller.guest.home.models.GuestModelMapper;
import project.fashionecommerce.backend.fashionecommerceproject.dto.authentication.MyAuthentication;
import project.fashionecommerce.backend.fashionecommerceproject.util.JwtUtils;
import project.fashionecommerce.backend.fashionecommerceproject.service.authentication.AuthenticationUseCaseService;
@RestController
@RequiredArgsConstructor
public class AuthenticationController implements AuthenticationAPI {
    @NonNull final AuthenticationUseCaseService authenticationUseCaseService;
    @NonNull final JwtUtils jwtUtils;
    @NonNull final GuestModelMapper guestModelMapper;
    @Override
    public ResponseEntity<?> refreshToken(HttpServletRequest request) {
        String refreshToken = jwtUtils.getJwtRefreshFromCookies(request);

        if ((refreshToken != null) && (refreshToken.length() > 0)) {
            String jwtCookieString = authenticationUseCaseService.refreshToken(refreshToken);
            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, jwtCookieString)
                    .body("Token is refreshed successfully!");
        }
        return ResponseEntity.badRequest().body("Refresh Token is empty!");
    }
    public ResponseEntity<?> logout() {
        MyAuthentication myAuthentication = authenticationUseCaseService.logout();
        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, myAuthentication.usernameCookieString())
                .header(HttpHeaders.SET_COOKIE, myAuthentication.jwtCookieString())
                .header(HttpHeaders.SET_COOKIE, myAuthentication.jwtRefreshCookieString())
                .body("You've been signed out!");
    }
}
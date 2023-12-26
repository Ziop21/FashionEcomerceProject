package project.fashionecommerce.backend.fashionecommerceproject.controller.authen;

import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import project.fashionecommerce.backend.fashionecommerceproject.controller.authen.models.JwtRefreshRequest;
import project.fashionecommerce.backend.fashionecommerceproject.controller.authen.models.JwtRefreshResponse;
import project.fashionecommerce.backend.fashionecommerceproject.controller.guest.authen.models.GuestAuthenModelMapper;
import project.fashionecommerce.backend.fashionecommerceproject.util.JwtUtils;
import project.fashionecommerce.backend.fashionecommerceproject.service.auth.AuthenUseCaseService;
@RestController
@RequiredArgsConstructor
public class AuthenController implements AuthenAPI {
    @NonNull final AuthenUseCaseService authenUseCaseService;
    @NonNull final JwtUtils jwtUtils;
    @NonNull final GuestAuthenModelMapper guestModelMapper;

    public ResponseEntity<?> logout() {
        authenUseCaseService.logout();
        return ResponseEntity
                .ok()
                .body("You've been signed out!");
    }

    @Override
    public ResponseEntity<?> refreshToken(JwtRefreshRequest jwtRefreshRequest) {
        String refreshToken = jwtRefreshRequest.refreshJWT();

        if ((refreshToken != null) && (refreshToken.length() > 0)) {
            String jwt = authenUseCaseService.refreshToken(refreshToken);

            return ResponseEntity.ok()
                    .body(new JwtRefreshResponse(jwt));
        }
        return ResponseEntity.badRequest().body("Refresh Token is empty!");
    }
}
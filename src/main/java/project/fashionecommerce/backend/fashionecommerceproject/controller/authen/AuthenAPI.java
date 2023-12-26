package project.fashionecommerce.backend.fashionecommerceproject.controller.authen;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import project.fashionecommerce.backend.fashionecommerceproject.controller.authen.models.JwtRefreshRequest;
import project.fashionecommerce.backend.fashionecommerceproject.controller.authen.models.JwtRefreshResponse;

@RequestMapping("/api")
public interface AuthenAPI {
    @PostMapping("/auth/logout")
    ResponseEntity<?> logout();

    @PostMapping("/auth/refresh-token")
     ResponseEntity<?> refreshToken(
             @RequestBody @Valid JwtRefreshRequest jwtRefreshRequest
    );
}

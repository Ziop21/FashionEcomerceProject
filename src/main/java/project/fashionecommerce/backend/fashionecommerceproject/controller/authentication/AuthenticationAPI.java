package project.fashionecommerce.backend.fashionecommerceproject.controller.authentication;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api")
public interface AuthenticationAPI {
    @PostMapping("/auth/logout")
    ResponseEntity<?> logout();

    @PostMapping("/auth/refresh-token")
     ResponseEntity<?> refreshToken(HttpServletRequest request);
}

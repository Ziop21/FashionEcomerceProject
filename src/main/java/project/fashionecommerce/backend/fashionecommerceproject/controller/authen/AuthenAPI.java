package project.fashionecommerce.backend.fashionecommerceproject.controller.authen;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api")
public interface AuthenAPI {
    @PostMapping("/auth/logout")
    ResponseEntity<?> logout();

    @PostMapping("/auth/refresh-token")
     ResponseEntity<?> refreshToken(HttpServletRequest request);
}

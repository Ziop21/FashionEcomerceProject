package project.fashionecommerce.backend.fashionecommerceproject.controller.authentication;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.fashionecommerce.backend.fashionecommerceproject.controller.authentication.models.LoginRequest;
import project.fashionecommerce.backend.fashionecommerceproject.controller.authentication.models.RegisterRequest;

@RequestMapping("/api")
public interface AuthenticationAPI {
    @PostMapping("/auth/login")
    ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest);
    @PostMapping("/auth/logout")
    ResponseEntity<?> logout();

    @PostMapping("/auth/refresh-token")
     ResponseEntity<?> refreshToken(HttpServletRequest request);

    @PostMapping("/guest/register")
    ResponseEntity<?>  register(@RequestBody @Valid RegisterRequest registerRequest);

    @PostMapping("/guest/register/send-token/{email}")
    ResponseEntity<?> sendToken(@PathVariable @Email String email);

    @GetMapping("/guest/register/verify")
    ResponseEntity<?> verifyEmailToken(
            @RequestParam(required = false, value = "token", defaultValue = "") String token
    );
}

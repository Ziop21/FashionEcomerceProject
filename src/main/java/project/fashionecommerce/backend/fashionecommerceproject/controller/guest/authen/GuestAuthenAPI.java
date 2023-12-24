package project.fashionecommerce.backend.fashionecommerceproject.controller.guest.authen;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.fashionecommerce.backend.fashionecommerceproject.controller.guest.authen.models.ChangePasswordRequest;
import project.fashionecommerce.backend.fashionecommerceproject.controller.guest.authen.models.LoginRequest;
import project.fashionecommerce.backend.fashionecommerceproject.controller.guest.authen.models.RegisterRequest;

@RequestMapping("/api/guest/auth")
public interface GuestAuthenAPI {
    @PostMapping("/login")
    ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest);

    @PostMapping("/register")
    ResponseEntity<?>  register(@RequestBody @Valid RegisterRequest registerRequest);

    @PostMapping("/register/send-token/{email}")
    ResponseEntity<?> sendToken(@PathVariable @Email String email);

    @PostMapping("/register/verify")
    ResponseEntity<?> verifyEmailToken(
            @RequestParam(required = false, value = "token", defaultValue = "") String token
    );

    @PostMapping("/forgot-password/send-token/{email}")
    ResponseEntity<?> sendTokenForgotPassword(@PathVariable @Email String email);

    @PostMapping("/forgot-password/verify")
    ResponseEntity<?> verifyForgotPasswordToken(
            @RequestParam(required = true, value = "token", defaultValue = "") String token,
            @RequestBody @Valid ChangePasswordRequest changePasswordRequest
    );

    @PostMapping("/forgot-password/find-email")
    ResponseEntity<?> findEmail(
            @RequestParam(required = false, value = "token", defaultValue = "") String token
    );
}

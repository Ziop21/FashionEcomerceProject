package project.fashionecommerce.backend.fashionecommerceproject.controller.guest.home;

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
import project.fashionecommerce.backend.fashionecommerceproject.controller.guest.home.models.LoginRequest;
import project.fashionecommerce.backend.fashionecommerceproject.controller.guest.home.models.RegisterRequest;

@RequestMapping("/api/guest")
public interface GuestAPI {

    @PostMapping("/auth/login")
    ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest);
    @GetMapping
    ResponseEntity<?> index(HttpServletRequest request);
    @PostMapping("/auth/register")
    ResponseEntity<?>  register(@RequestBody @Valid RegisterRequest registerRequest);

    @PostMapping("/auth/register/send-token/{email}")
    ResponseEntity<?> sendToken(@PathVariable @Email String email);

    @GetMapping("/auth/register/verify")
    ResponseEntity<?> verifyEmailToken(
            @RequestParam(required = false, value = "token", defaultValue = "") String token
    );
}

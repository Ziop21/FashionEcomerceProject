package project.fashionecomerce.backend.fashionecomerceproject.controller.account;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.fashionecomerce.backend.fashionecomerceproject.controller.account.models.RegisterRequest;

@RequestMapping("/register")
public interface RegisterAPI {
    @PostMapping
    void register(@RequestBody @Valid RegisterRequest registerRequest);

    @PostMapping("/send-token/{email}")
    ResponseEntity<String> sendToken(@PathVariable @Email String email);

    @GetMapping("/verify")
    ResponseEntity<String> verifyToken(
            @RequestParam(required = false, value = "token", defaultValue = "") String token,
            @RequestParam(required = false, value = "email", defaultValue = "") @Email String email
    );

}

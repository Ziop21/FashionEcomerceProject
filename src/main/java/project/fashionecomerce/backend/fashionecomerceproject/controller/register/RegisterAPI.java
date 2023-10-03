package project.fashionecomerce.backend.fashionecomerceproject.controller.register;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import project.fashionecomerce.backend.fashionecomerceproject.controller.register.models.RegisterRequest;

@RequestMapping("/register")
public interface RegisterAPI {
    @PostMapping
    void register(@RequestBody @Valid RegisterRequest registerRequest);

    @PostMapping("/send-token/{email}")
    void sendToken(@PathVariable @Email String email);

    @PutMapping("/verify?token={token}&email={email}")
    String verifyToken(@PathVariable String token, @PathVariable String email);

}

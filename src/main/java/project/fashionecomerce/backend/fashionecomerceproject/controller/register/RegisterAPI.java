package project.fashionecomerce.backend.fashionecomerceproject.controller.register;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import project.fashionecomerce.backend.fashionecomerceproject.controller.register.models.RegisterRequest;

@RequestMapping("/register")
public interface RegisterAPI {
    @PostMapping
    void register(@RequestBody @Valid RegisterRequest registerRequest);

}

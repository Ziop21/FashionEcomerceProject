package project.fashionecomerce.backend.fashionecomerceproject.controller.register;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import project.fashionecomerce.backend.fashionecomerceproject.controller.register.models.RegisterRequest;

@RestController
@RequiredArgsConstructor
public class RegisterController implements RegisterAPI {

    @Override
    public void register(RegisterRequest registerRequest) {

    }
}

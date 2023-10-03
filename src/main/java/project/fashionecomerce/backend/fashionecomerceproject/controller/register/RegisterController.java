package project.fashionecomerce.backend.fashionecomerceproject.controller.register;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import project.fashionecomerce.backend.fashionecomerceproject.controller.register.models.RegisterRequest;
import project.fashionecomerce.backend.fashionecomerceproject.service.register.RegisterUseCaseService;

@RestController
@RequiredArgsConstructor
public class RegisterController implements RegisterAPI {
    @NonNull final RegisterUseCaseService registerUseCaseService;
    @Override
    public void register(RegisterRequest registerRequest) {

    }

    @Override
    public void sendToken(String email) {
        registerUseCaseService.sendToken(email);
    }

    @Override
    public String verifyToken(String token, String email) {
        String userEmail = registerUseCaseService.verifyToken(token, email);
        return userEmail;
    }
}

package project.fashionecomerce.backend.fashionecomerceproject.controller.register;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> sendToken(String email) {
        registerUseCaseService.sendToken(email);
        return new ResponseEntity<>(email, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> verifyToken(String token, String email) {
        registerUseCaseService.verifyToken(token, email);
        return new ResponseEntity<>(email, HttpStatus.OK);
    }
}

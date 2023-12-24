package project.fashionecommerce.backend.fashionecommerceproject.controller.guest.authen;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import project.fashionecommerce.backend.fashionecommerceproject.controller.guest.authen.models.ChangePasswordRequest;
import project.fashionecommerce.backend.fashionecommerceproject.controller.guest.authen.models.GuestAuthenModelMapper;
import project.fashionecommerce.backend.fashionecommerceproject.controller.guest.authen.models.LoginRequest;
import project.fashionecommerce.backend.fashionecommerceproject.controller.guest.authen.models.RegisterRequest;
import project.fashionecommerce.backend.fashionecommerceproject.controller.guest.home.models.UserInfoResponse;
import project.fashionecommerce.backend.fashionecommerceproject.dto.authen.Login;
import project.fashionecommerce.backend.fashionecommerceproject.dto.authen.MyAuthentication;
import project.fashionecommerce.backend.fashionecommerceproject.dto.authen.Register;
import project.fashionecommerce.backend.fashionecommerceproject.service.guest.GuestAuthenUseCaseService;
import project.fashionecommerce.backend.fashionecommerceproject.service.guest.GuestUseCaseService;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class GuestAuthenController implements GuestAuthenAPI {
    @NonNull final GuestAuthenModelMapper guestAuthenModelMapper;
    @NonNull final GuestAuthenUseCaseService guestAuthenUseCaseService;
    @NonNull final GuestUseCaseService guestUseCaseService;

    @Override
    public ResponseEntity<?> login(LoginRequest loginRequest) {
        Login login = guestAuthenModelMapper.toDto(loginRequest);
        MyAuthentication myAuthentication = guestAuthenUseCaseService.login(login);

        List<String> roles = myAuthentication.userDetails().getRoles();

        return ResponseEntity
                .ok()
                .body(new UserInfoResponse(myAuthentication.jwt(), myAuthentication.jwtRefresh()));
    }
    @Override
    public ResponseEntity<?> register(RegisterRequest registerRequest) {
        Register register = guestAuthenModelMapper.toDto(registerRequest);
        String status = guestAuthenUseCaseService.register(register);
        return ResponseEntity.ok().body(status);
    }
    @Override
    public ResponseEntity<?> sendToken(String email) {
        String status = guestAuthenUseCaseService.sendToken(email, false);
        return ResponseEntity.ok().body(status);
    }
    @Override
    public ResponseEntity<?> verifyEmailToken(String token) {
        String status = guestAuthenUseCaseService.verifyToken(token);
        return ResponseEntity.ok().body(status);
    }

    @Override
    public ResponseEntity<?> sendTokenForgotPassword(String email) {
        String status = guestAuthenUseCaseService.sendToken(email, true);
        return ResponseEntity.ok().body(status);
    }

    @Override
    public ResponseEntity<?> verifyForgotPasswordToken(String token, ChangePasswordRequest changePasswordRequest) {
        Register register = guestAuthenModelMapper.toDto(changePasswordRequest);
        String status = guestAuthenUseCaseService.verifyForgotPasswordToken(token, register);
        return ResponseEntity.ok().body(status);
    }

    @Override
    public ResponseEntity<?> findEmail(String token) {
        String email = guestAuthenUseCaseService.findEmail(token);
        return ResponseEntity.ok().body(email);
    }
}

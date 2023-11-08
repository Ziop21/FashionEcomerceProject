package project.fashionecommerce.backend.fashionecommerceproject.controller.guest.authen;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
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
import java.util.stream.Collectors;

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

        List<String> roles = myAuthentication.userDetails().getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, myAuthentication.jwtCookieString(),
                        myAuthentication.jwtRefreshCookieString(),
                        myAuthentication.usernameCookieString())
//                .headers(headers)
                .body(new UserInfoResponse(myAuthentication.userDetails().getId(),
                        myAuthentication.userDetails().getUsername(),
                        myAuthentication.userDetails().getEmail(),
                        roles));
    }
    @Override
    public ResponseEntity<?> register(RegisterRequest registerRequest) {
        Register register = guestAuthenModelMapper.toDto(registerRequest);
        String status = guestAuthenUseCaseService.register(register);
        return ResponseEntity.ok().body(status);
    }
    @Override
    public ResponseEntity<?> sendToken(String email) {
        String status = guestAuthenUseCaseService.sendToken(email);
        return ResponseEntity.ok().body(status);
    }
    @Override
    public ResponseEntity<?> verifyEmailToken(String token) {
        String status = guestAuthenUseCaseService.verifyToken(token);
        return ResponseEntity.ok().body(status);
    }
}

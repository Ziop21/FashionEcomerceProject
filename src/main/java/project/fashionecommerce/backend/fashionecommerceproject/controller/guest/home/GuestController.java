package project.fashionecommerce.backend.fashionecommerceproject.controller.guest.home;

import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import project.fashionecommerce.backend.fashionecommerceproject.controller.guest.home.models.GuestModelMapper;
import project.fashionecommerce.backend.fashionecommerceproject.controller.guest.home.models.LoginRequest;
import project.fashionecommerce.backend.fashionecommerceproject.controller.guest.home.models.RegisterRequest;
import project.fashionecommerce.backend.fashionecommerceproject.controller.guest.home.models.UserInfoResponse;
import project.fashionecommerce.backend.fashionecommerceproject.dto.authentication.Login;
import project.fashionecommerce.backend.fashionecommerceproject.dto.authentication.MyAuthentication;
import project.fashionecommerce.backend.fashionecommerceproject.dto.authentication.Register;
import project.fashionecommerce.backend.fashionecommerceproject.service.authentication.AuthenticationUseCaseService;
import project.fashionecommerce.backend.fashionecommerceproject.service.guest.GuestUseCaseService;
import project.fashionecommerce.backend.fashionecommerceproject.util.JwtUtils;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class GuestController implements GuestAPI{
    @NonNull final AuthenticationUseCaseService authenticationUseCaseService;
    @NonNull final JwtUtils jwtUtils;
    @NonNull final GuestModelMapper guestModelMapper;
    @NonNull final GuestUseCaseService guestUseCaseService;

    @Override
    public ResponseEntity<?> index(HttpServletRequest request) {
        MyAuthentication myAuthentication = guestUseCaseService.index(request);
        if (myAuthentication.userDetails() == null) {
            if (myAuthentication.cartTokenCookieString() == null)
                return ResponseEntity.ok().body("Home page");
            return ResponseEntity
                    .ok()
                    .header(HttpHeaders.SET_COOKIE, myAuthentication.cartTokenCookieString())
                    .body("Home page");
        }
        List<String> roles = myAuthentication.userDetails().getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        return ResponseEntity
                .ok()
                .body(new UserInfoResponse(myAuthentication.userDetails().getId(),
                        myAuthentication.userDetails().getUsername(),
                        myAuthentication.userDetails().getEmail(),
                        roles));
    }
    @Override
    public ResponseEntity<?> login(LoginRequest loginRequest) {
        Login login = guestModelMapper.toDto(loginRequest);
        MyAuthentication myAuthentication = guestUseCaseService.login(login);

        List<String> roles = myAuthentication.userDetails().getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        if (myAuthentication.jwtCookieString() == null || myAuthentication.jwtRefreshCookieString() == null)
            return ResponseEntity
                    .ok()
                    .body(new UserInfoResponse(myAuthentication.userDetails().getId(),
                            myAuthentication.userDetails().getUsername(),
                            myAuthentication.userDetails().getEmail(),
                            roles));
        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, myAuthentication.usernameCookieString())
                .header(HttpHeaders.SET_COOKIE, myAuthentication.jwtCookieString())
                .header(HttpHeaders.SET_COOKIE, myAuthentication.jwtRefreshCookieString())
                .body(new UserInfoResponse(myAuthentication.userDetails().getId(),
                        myAuthentication.userDetails().getUsername(),
                        myAuthentication.userDetails().getEmail(),
                        roles));
    }
    @Override
    public ResponseEntity<?> register(RegisterRequest registerRequest) {
        Register register = guestModelMapper.toDto(registerRequest);
        String status = guestUseCaseService.register(register);
        return ResponseEntity.ok().body(status);
    }
    @Override
    public ResponseEntity<?> sendToken(String email) {
        String status = guestUseCaseService.sendToken(email);
        return ResponseEntity.ok().body(status);
    }
    @Override
    public ResponseEntity<?> verifyEmailToken(String token) {
        String status = guestUseCaseService.verifyToken(token);
        return ResponseEntity.ok().body(status);
    }
}

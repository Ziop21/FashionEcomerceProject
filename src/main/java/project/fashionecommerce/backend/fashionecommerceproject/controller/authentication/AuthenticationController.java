package project.fashionecommerce.backend.fashionecommerceproject.controller.authentication;

import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import project.fashionecommerce.backend.fashionecommerceproject.controller.authentication.models.AuthenticationModelMapper;
import project.fashionecommerce.backend.fashionecommerceproject.controller.authentication.models.LoginRequest;
import project.fashionecommerce.backend.fashionecommerceproject.controller.authentication.models.RegisterRequest;
import project.fashionecommerce.backend.fashionecommerceproject.controller.authentication.models.UserInfoResponse;
import project.fashionecommerce.backend.fashionecommerceproject.dto.authentication.Login;
import project.fashionecommerce.backend.fashionecommerceproject.dto.authentication.MyAuthentication;
import project.fashionecommerce.backend.fashionecommerceproject.dto.authentication.Register;
import project.fashionecommerce.backend.fashionecommerceproject.util.JwtUtils;
import project.fashionecommerce.backend.fashionecommerceproject.service.authentication.AuthenticationUseCaseService;
@RestController
@RequiredArgsConstructor
public class AuthenticationController implements AuthenticationAPI {
    @NonNull final AuthenticationUseCaseService authenticationUseCaseService;
    @NonNull final JwtUtils jwtUtils;
    @NonNull final AuthenticationModelMapper authenticationModelMapper;
    @Override
    public ResponseEntity<?> login(LoginRequest loginRequest) {
        Login login = authenticationModelMapper.toDto(loginRequest);

        MyAuthentication myAuthentication = authenticationUseCaseService.login(login);

        List<String> roles = myAuthentication.userDetails().getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, myAuthentication.jwtCookieString())
                .header(HttpHeaders.SET_COOKIE, myAuthentication.jwtRefreshCookieString())
                .body(new UserInfoResponse(myAuthentication.userDetails().getId(),
                        myAuthentication.userDetails().getUsername(),
                        myAuthentication.userDetails().getEmail(),
                        roles));
    }
    @Override
    public ResponseEntity<?> refreshToken(HttpServletRequest request) {
        String refreshToken = jwtUtils.getJwtRefreshFromCookies(request);

        if ((refreshToken != null) && (refreshToken.length() > 0)) {
            String jwtCookieString = authenticationUseCaseService.refreshToken(refreshToken);
            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, jwtCookieString)
                    .body("Token is refreshed successfully!");
        }

        return ResponseEntity.badRequest().body("Refresh Token is empty!");
    }
    public ResponseEntity<?> logout() {
        MyAuthentication myAuthentication = authenticationUseCaseService.logout();
        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, myAuthentication.jwtCookieString())
                .header(HttpHeaders.SET_COOKIE, myAuthentication.jwtRefreshCookieString())
                .body("You've been signed out!");
    }
    @Override
    public ResponseEntity<?> register(RegisterRequest registerRequest) {
        Register register = authenticationModelMapper.toDto(registerRequest);
        authenticationUseCaseService.register(register);
        return ResponseEntity.ok().body("Đăng ký thành công");
    }
    @Override
    public ResponseEntity<?> sendToken(String email) {
        authenticationUseCaseService.sendToken(email);
        return ResponseEntity.ok().body("Gửi token thành công");
    }
    @Override
    public ResponseEntity<?> verifyEmailToken(String token) {
        authenticationUseCaseService.verifyToken(token);
        return ResponseEntity.ok().body("Xác nhận thành công");
    }

}
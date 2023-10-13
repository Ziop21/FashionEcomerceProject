package project.fashionecommerce.backend.fashionecommerceproject.service.authentication;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.fashionecommerce.backend.fashionecommerceproject.dto.authentication.Login;
import project.fashionecommerce.backend.fashionecommerceproject.dto.authentication.MyAuthentication;
import project.fashionecommerce.backend.fashionecommerceproject.dto.authentication.Register;
import project.fashionecommerce.backend.fashionecommerceproject.dto.enums.ERole;
import project.fashionecommerce.backend.fashionecommerceproject.dto.role.Role;
import project.fashionecommerce.backend.fashionecommerceproject.dto.token.Token;
import project.fashionecommerce.backend.fashionecommerceproject.dto.user.UserMapper;
import project.fashionecommerce.backend.fashionecommerceproject.dto.user.User;
import project.fashionecommerce.backend.fashionecommerceproject.exception.MyConfirmPasswordUnmatchException;
import project.fashionecommerce.backend.fashionecommerceproject.exception.MyConflictsException;
import project.fashionecommerce.backend.fashionecommerceproject.exception.MyInvalidTokenException;
import project.fashionecommerce.backend.fashionecommerceproject.exception.MyResourceNotFoundException;
import project.fashionecommerce.backend.fashionecommerceproject.exception.TokenRefreshException;
import project.fashionecommerce.backend.fashionecommerceproject.service.token.TokenCommandService;
import project.fashionecommerce.backend.fashionecommerceproject.service.token.TokenQueryService;
import project.fashionecommerce.backend.fashionecommerceproject.util.JwtUtils;
import project.fashionecommerce.backend.fashionecommerceproject.repository.security.user_detail.UserDetailsImpl;
import project.fashionecommerce.backend.fashionecommerceproject.service.user.UserCommandService;
import project.fashionecommerce.backend.fashionecommerceproject.service.user.UserQueryService;
import org.springframework.mail.javamail.JavaMailSender;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationUseCaseService {
    @NonNull final UserCommandService userCommandService;
    @NonNull final UserQueryService userQueryService;
    @NonNull final TokenQueryService tokenQueryService;
    @NonNull final TokenCommandService tokenCommandService;
    @NonNull final AuthenticationManager authenticationManager;
    @NonNull final UserMapper userMapper;
    @NonNull final JwtUtils jwtUtils;
    @Autowired final PasswordEncoder passwordEncoder;
    @Autowired private JavaMailSender javaMailSender;

    @Value("${fashion_ecomerce.app.jwtEmailExpirationMs}")
    private Long emailTokenDurationMs;
    @Value("${fashion_ecomerce.app.jwtRefreshExpirationMs}")
    private Long refreshTokenDurationMs;
    @Transactional
    public void sendToken(String email) {
        if (userQueryService.existsByEmail(email))
            throw new MyConflictsException();
        User user = User.builder()
                .email(email)
                .isEmailActive(false)
                .isDeleted(false)
                .build();
        user = userCommandService.save(user);

        Token token = tokenCommandService.save(user.id(), emailTokenDurationMs);
        this.sendEmailVerification(email, token);
    }

    @Transactional
    public void verifyToken(String token) {
        Token foundToken = tokenQueryService.findByToken(token).orElseThrow(MyResourceNotFoundException::new);
        if (!token.equals(foundToken.token())
        || foundToken.expiryDateTime().isBefore(LocalDateTime.now()))
            throw new MyInvalidTokenException();
        User user = userQueryService.findById(foundToken.userId());
        user = userMapper.updateDtoIsEmailActive(user, true);
        userCommandService.save(user);
        tokenCommandService.deleteByUserId(foundToken.userId());
    }
    @Transactional
    public MyAuthentication login(Login login) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(login.email(), login.password()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);
        Token refreshToken = tokenCommandService.save(userDetails.getId(), refreshTokenDurationMs);
        ResponseCookie jwtRefreshCookie = jwtUtils.generateRefreshJwtCookie(refreshToken.token());

        return new MyAuthentication(userDetails, jwtCookie.toString(), jwtRefreshCookie.toString());
    }

    @Transactional
    public String refreshToken(String refreshToken) {
        return tokenQueryService.findByToken(refreshToken)
                .map(tokenCommandService::verifyExpiration)
                .map(Token::userId)
                .map(userId -> {
                    User user = userQueryService.findById(userId);
                    ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(user);

                    return jwtCookie.toString();
                })
                .orElseThrow(() -> new TokenRefreshException(refreshToken,
                        "Refresh token is not in database!"));
    }

    @Transactional
    public MyAuthentication logout() {
        Object principle = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principle.toString() != "anonymousUser") {
            String userId = ((UserDetailsImpl) principle).getId();
            tokenCommandService.deleteByUserId(userId);
        }
        ResponseCookie jwtCookie = jwtUtils.getCleanJwtCookie();
        ResponseCookie jwtRefreshCookie = jwtUtils.getCleanJwtRefreshCookie();

        return new MyAuthentication(null, jwtCookie.toString(), jwtRefreshCookie.toString());
    }
    @Transactional
    public void register(Register register) {
        if (!register.confirmPassword().equals(register.password()))
            throw new MyConfirmPasswordUnmatchException();
        User user = userQueryService.findByEmail(register.email());
        String hashedPassword = passwordEncoder.encode(register.password());
        user = userMapper.updateDtoHashedPassword(user, hashedPassword);
        List<Role> roles = new ArrayList<>();
        roles.add(new Role(ERole.CUSTOMER));
        user = userMapper.updateDtoRoles(user, roles);
        userCommandService.save(user);
    }
    public void sendEmailVerification(String email, Token token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("FASHION ECOMMERCE: XÁC NHẬN ĐỊA CHỈ EMAIL");
        message.setText("Nhấn vào đường link sau để xác nhận email: "
                + "http://localhost:8081/api/auth/register/verify?token=" + token.token());
        javaMailSender.send(message);
    }
}

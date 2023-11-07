package project.fashionecommerce.backend.fashionecommerceproject.service.guest;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.fashionecommerce.backend.fashionecommerceproject.config.security.userDetails.Implement.UserDetailsImpl;
import project.fashionecommerce.backend.fashionecommerceproject.dto.authen.Login;
import project.fashionecommerce.backend.fashionecommerceproject.dto.authen.MyAuthentication;
import project.fashionecommerce.backend.fashionecommerceproject.dto.authen.Register;
import project.fashionecommerce.backend.fashionecommerceproject.dto.enums.ERole;
import project.fashionecommerce.backend.fashionecommerceproject.dto.role.Role;
import project.fashionecommerce.backend.fashionecommerceproject.dto.token.Token;
import project.fashionecommerce.backend.fashionecommerceproject.dto.user.User;
import project.fashionecommerce.backend.fashionecommerceproject.dto.user.UserId;
import project.fashionecommerce.backend.fashionecommerceproject.exception.MyConfirmPasswordUnmatchException;
import project.fashionecommerce.backend.fashionecommerceproject.exception.MyConflictsException;
import project.fashionecommerce.backend.fashionecommerceproject.exception.MyResourceNotFoundException;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.token.TokenCommandService;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.token.TokenQueryService;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.user.UserCommandService;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.user.UserQueryService;
import project.fashionecommerce.backend.fashionecommerceproject.util.JwtUtils;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GuestAuthenUseCaseService {
    @NonNull final GuestUseCaseService guestUseCaseService;
    @NonNull final JwtUtils jwtUtils;
    @NonNull final UserQueryService userQueryService;
    @NonNull final UserCommandService userCommandService;
    @NonNull final TokenCommandService tokenCommandService;
    @NonNull final TokenQueryService tokenQueryService;
    @NonNull final AuthenticationManager authenticationManager;

    @Autowired final PasswordEncoder passwordEncoder;
    @Autowired private JavaMailSender javaMailSender;

    @Value("${fashion_ecommerce.app.username}")
    private String usernameCookieName;
    @Value("${fashion_ecommerce.app.jwtRefreshExpirationMs}")
    private Long refreshTokenDurationMs;
    @Value("${fashion_ecommerce.app.jwtEmailExpirationS}")
    private Long emailTokenDurationS;

    @Transactional
    public MyAuthentication login(Login login) {
        String currentUser = guestUseCaseService.getCurrentUser();
        if (currentUser == "anonymousUser") {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(login.email(), login.password()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            ResponseCookie usernameCookie = jwtUtils.generateCookie(usernameCookieName, userDetails.getEmail(), "/api");
            ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails, "/api");
            Token refreshToken = tokenCommandService.save(userDetails.getId(), refreshTokenDurationMs);
            ResponseCookie jwtRefreshCookie = jwtUtils.generateRefreshJwtCookie(refreshToken.token(), "/api/auth/refresh-token");
            return new MyAuthentication(userDetails, null, usernameCookie.toString(), jwtCookie.toString(), jwtRefreshCookie.toString());
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return MyAuthentication.builder().userDetails(userDetails).build();
    }
    @Transactional
    public String register(Register register) {
        String currentUser = guestUseCaseService.getCurrentUser();
        if (currentUser == "anonymousUser") {
            if (!register.confirmPassword().equals(register.password()))
                throw new MyConfirmPasswordUnmatchException();
            User user = userQueryService.findByEmailAndIsEmailActive(register.email(), true);
            String hashedPassword = passwordEncoder.encode(register.password());
            userCommandService.updateHashedPasswordAndIsActive(new UserId(user.id()), hashedPassword, true);
            return "Success";
        }
        return "You have not signed out yet";

    }
    public void sendEmailVerification(String email, Token token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("FASHION ECOMMERCE WEBSITE: EMAIL CONFIRMATION");
        message.setText("Thank you for choosing our website!! Please click the following link to verify your email: "
                + "http://localhost:8081/api/guest/auth/register/verify?token=" + token.token());
        javaMailSender.send(message);
    }
    @Transactional
    public String sendToken(String email) {
        String currentUser = guestUseCaseService.getCurrentUser();
        if (currentUser == "anonymousUser") {
            if (userQueryService.existsByEmail(email))
                throw new MyConflictsException();
            List<Role> roles = new ArrayList<>();
            roles.add(new Role(ERole.CUSTOMER));
            User user = User.builder()
                    .email(email)
                    .roles(roles)
                    .isEmailActive(false)
                    .isActive(false)
                    .isDeleted(false)
                    .build();
            user = userCommandService.save(user);

            Token token = tokenCommandService.save(user.id(), emailTokenDurationS);
            this.sendEmailVerification(email, token);
            return "Success";
        }
        return "You have not signed out yet";
    }

    @Transactional
    public String verifyToken(String token) {
        String currentUser = guestUseCaseService.getCurrentUser();
        if (currentUser == "anonymousUser") {
            Token foundToken = tokenQueryService.findByToken(token).orElseThrow(MyResourceNotFoundException::new);
            foundToken = tokenCommandService.verifyExpiration(foundToken);
            userCommandService.updateIsEmailActive(new UserId(foundToken.userId()), true);
            tokenCommandService.deleteByUserId(foundToken.userId());
            return "Success";
        }
        return "You have not signed out yet";
    }
}

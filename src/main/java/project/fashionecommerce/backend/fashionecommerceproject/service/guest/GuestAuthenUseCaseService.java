package project.fashionecommerce.backend.fashionecommerceproject.service.guest;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import project.fashionecommerce.backend.fashionecommerceproject.dto.user.UserMapper;
import project.fashionecommerce.backend.fashionecommerceproject.exception.MyConfirmPasswordUnmatchException;
import project.fashionecommerce.backend.fashionecommerceproject.exception.MyConflictsException;
import project.fashionecommerce.backend.fashionecommerceproject.exception.MyForbiddenException;
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

    @NonNull final UserMapper userMapper;

    @Autowired final PasswordEncoder passwordEncoder;
    @Autowired private JavaMailSender javaMailSender;

    @Value("${fashion_ecommerce.app.jwtRefreshExpirationMs}")
    private Long refreshTokenDurationMs;

    @Value("${fashion_ecommerce.app.jwtEmailForgotPasswordExpirationS}")
    private Long emailForgotPasswordExpirationS;
    @Value("${fashion_ecommerce.app.jwtEmailExpirationS}")
    private Long emailTokenDurationMs;
    @Value("${fashion_ecommerce.app.FE_SERVER.url}")
    private String FE_SERVER_URL;

    @Transactional
    public MyAuthentication login(Login login) {
        String currentUser = guestUseCaseService.getCurrentUser();
        if (currentUser == "anonymousUser") {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(login.email(), login.password()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            String jwt = jwtUtils.generateTokenFromUserDetail(userDetails);
            Token refreshToken = tokenCommandService.save(userDetails.getId(), refreshTokenDurationMs);
            String jwtRefresh = jwtUtils.generateRefreshJWT(refreshToken.token());
            return new MyAuthentication(userDetails, null, jwt, jwtRefresh.toString());
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
            if (user.isActive()) {
                throw new MyForbiddenException();
            }
            user = userMapper.updateDto(user, register.firstName(), register.lastName(), null, List.of(register.phone()), List.of(register.address()), null, null);
            userCommandService.update(new UserId(user.id()), user);
            String hashedPassword = passwordEncoder.encode(register.password());
            userCommandService.updateHashedPasswordAndIsActive(new UserId(user.id()), hashedPassword, true);
            return "Success";
        }
        return "You have not signed out yet";

    }
    public void sendEmailVerification(String email, Token token, Boolean isResetPassword) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("FASHION ECOMMERCE WEBSITE: EMAIL CONFIRMATION");
        if (isResetPassword) {
            message.setText("Thank you for choosing our website!! Please click the following link to verify your email: "
                    + FE_SERVER_URL + "/forgot-password/verify?token=" + token.token());
        }
        else {
            message.setText("Thank you for choosing our website!! Please click the following link to verify your email: "
                    + FE_SERVER_URL + "/register/verify?token=" + token.token());
        }
        javaMailSender.send(message);
    }
    @Transactional
    public String sendToken(String email, Boolean isResetPassword) {
        String currentUser = guestUseCaseService.getCurrentUser();
        if (currentUser == "anonymousUser") {
            if (!isResetPassword){
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

                Token token = tokenCommandService.save(user.id(), emailTokenDurationMs);
                this.sendEmailVerification(email, token, false);
                return "Success";
            }
            User user = userQueryService.findByEmailAndIsEmailActive(email, true);
            if (!user.isActive()) {
                throw new MyForbiddenException();
            }
            tokenCommandService.deleteByUserId(user.id());
            Token token = tokenCommandService.save(user.id(), emailForgotPasswordExpirationS);
            this.sendEmailVerification(email, token, true);
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
            User user = userQueryService.findById(new UserId(foundToken.userId()));
            tokenCommandService.deleteByUserId(foundToken.userId());
            return user.email();
        }
        throw new MyForbiddenException();
    }

    public String verifyForgotPasswordToken(String token, Register register) {
        String currentUser = guestUseCaseService.getCurrentUser();
        if (currentUser == "anonymousUser") {
            Token foundToken = tokenQueryService.findByToken(token).orElseThrow(MyResourceNotFoundException::new);
            foundToken = tokenCommandService.verifyExpiration(foundToken);
            tokenCommandService.deleteByUserId(foundToken.userId());
            if (!register.confirmPassword().equals(register.password()))
                throw new MyConfirmPasswordUnmatchException();
            User user = userQueryService.findByEmailAndIsEmailActive(register.email(), true);
            if (!user.isActive()) {
                throw new MyForbiddenException();
            }
            String hashedPassword = passwordEncoder.encode(register.password());
            userCommandService.updateHashedPasswordAndIsActive(new UserId(user.id()), hashedPassword, true);
            return user.email();
        }
        throw new MyForbiddenException();
    }

    public String findEmail(String token) {
        String currentUser = guestUseCaseService.getCurrentUser();
        if (currentUser == "anonymousUser") {
            Token foundToken = tokenQueryService.findByToken(token).orElseThrow(MyResourceNotFoundException::new);
            User user = userQueryService.findById(new UserId(foundToken.userId()));
            return user.email();
        }
        throw new MyForbiddenException();
    }
}

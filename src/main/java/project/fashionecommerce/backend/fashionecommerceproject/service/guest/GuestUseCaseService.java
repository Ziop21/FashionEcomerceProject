package project.fashionecommerce.backend.fashionecommerceproject.service.guest;

import jakarta.servlet.http.HttpServletRequest;
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
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.fashionecommerce.backend.fashionecommerceproject.dto.authentication.Login;
import project.fashionecommerce.backend.fashionecommerceproject.dto.authentication.MyAuthentication;
import project.fashionecommerce.backend.fashionecommerceproject.dto.authentication.Register;
import project.fashionecommerce.backend.fashionecommerceproject.dto.cart.Cart;
import project.fashionecommerce.backend.fashionecommerceproject.dto.enums.ERole;
import project.fashionecommerce.backend.fashionecommerceproject.dto.role.Role;
import project.fashionecommerce.backend.fashionecommerceproject.dto.token.Token;
import project.fashionecommerce.backend.fashionecommerceproject.dto.user.User;
import project.fashionecommerce.backend.fashionecommerceproject.dto.user.UserMapper;
import project.fashionecommerce.backend.fashionecommerceproject.exception.MyConfirmPasswordUnmatchException;
import project.fashionecommerce.backend.fashionecommerceproject.exception.MyConflictsException;
import project.fashionecommerce.backend.fashionecommerceproject.exception.MyForbiddenException;
import project.fashionecommerce.backend.fashionecommerceproject.exception.MyResourceNotFoundException;
import project.fashionecommerce.backend.fashionecommerceproject.config.security.userDetails.Implement.UserDetailsImpl;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.cart.CartCommandService;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.cart.CartQueryService;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.token.TokenCommandService;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.token.TokenQueryService;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.user.UserCommandService;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.user.UserQueryService;
import project.fashionecommerce.backend.fashionecommerceproject.util.JwtUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GuestUseCaseService {
    @NonNull final UserCommandService userCommandService;
    @NonNull final TokenCommandService tokenCommandService;
    @NonNull final CartCommandService cartCommandService;

    @NonNull final TokenQueryService tokenQueryService;
    @NonNull final UserQueryService userQueryService;
    @NonNull final CartQueryService cartQueryService;

    @NonNull final JwtUtils jwtUtils;
    @NonNull final AuthenticationManager authenticationManager;
    @Autowired private JavaMailSender javaMailSender;
    @Autowired final PasswordEncoder passwordEncoder;

    @NonNull final UserMapper userMapper;

    @Value("${fashion_ecommerce.app.jwtRefreshExpirationMs}")
    private Long refreshTokenDurationMs;
    @Value("${fashion_ecommerce.app.jwtEmailExpirationS}")
    private Long emailTokenDurationS;
    @Value("${fashion_ecommerce.app.jwtCartCookieName}")
    private String cartTokenCookieName;

    @Transactional
    public MyAuthentication index(HttpServletRequest request) {
        String currentUser = Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getPrincipal).get().toString();
        if (currentUser == "anonymousUser") {
            String oldCartToken = jwtUtils.getCookieValueByName(request, cartTokenCookieName);
            if (oldCartToken == null || jwtUtils.validateJwtToken(oldCartToken) == false) {
                Cart cart = Cart.builder().isDeleted(false).build();
                cart = cartCommandService.save(cart);
                String cartToken = jwtUtils.generateTokenFromCartId(cart.id());
                ResponseCookie cartTokenCookie = jwtUtils.generateCookie(cartTokenCookieName, cartToken, "/api");
                return MyAuthentication.builder().cartTokenCookieString(cartTokenCookie.toString()).build();
            }
            return MyAuthentication.builder().build();
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String oldCartToken = jwtUtils.getCookieValueByName(request, cartTokenCookieName);
        if (jwtUtils.validateJwtToken(oldCartToken) == false) {
            throw new MyForbiddenException();
        }
        return MyAuthentication.builder().userDetails(userDetails).build();
    }

    @Transactional
    public MyAuthentication login(Login login) {
        String currentUser = Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getPrincipal).get().toString();
        if (currentUser != "anonymousUser") {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            return MyAuthentication.builder().userDetails(userDetails).build();
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(login.email(), login.password()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String username = userDetails.getUsername() == null
                ? userDetails.getEmail() : userDetails.getUsername();

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        ResponseCookie usernameCookie = jwtUtils.generateCookie("username", username, "/api");
        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails, "/api");
        Token refreshToken = tokenCommandService.save(userDetails.getId(), refreshTokenDurationMs);
        ResponseCookie jwtRefreshCookie = jwtUtils.generateRefreshJwtCookie(refreshToken.token(), "/api/auth/refresh-token");
        return new MyAuthentication(userDetails, null, usernameCookie.toString(), jwtCookie.toString(), jwtRefreshCookie.toString());
    }
    @Transactional
    public String register(Register register) {
        String currentUser = Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getPrincipal).get().toString();
        if (currentUser != "anonymousUser") {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            return "You have not signed out yet";
        }
        if (!register.confirmPassword().equals(register.password()))
            throw new MyConfirmPasswordUnmatchException();
        User user = userQueryService.findByEmail(register.email());
        String hashedPassword = passwordEncoder.encode(register.password());
        user = userMapper.updateDtoHashedPassword(user, hashedPassword);
        List<Role> roles = new ArrayList<>();
        roles.add(new Role(ERole.CUSTOMER));
        user = userMapper.updateDtoRoles(user, roles);
        userCommandService.save(user);
        return "Success";
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
        String currentUser = Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getPrincipal).get().toString();
        if (currentUser != "anonymousUser") {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            return "You have not signed out yet";
        }
        if (userQueryService.existsByEmail(email))
            throw new MyConflictsException();
        User user = User.builder()
                .email(email)
                .isEmailActive(false)
                .isDeleted(false)
                .build();
        user = userCommandService.save(user);

        Token token = tokenCommandService.save(user.id(), emailTokenDurationS);
        this.sendEmailVerification(email, token);
        return "Success";
    }

    @Transactional
    public String verifyToken(String token) {
        String currentUser = Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getPrincipal).get().toString();
        if (currentUser != "anonymousUser") {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            return "You have not signed out yet";
        }
        Token foundToken = tokenQueryService.findByToken(token).orElseThrow(MyResourceNotFoundException::new);
        foundToken = tokenCommandService.verifyExpiration(foundToken);
        User user = userQueryService.findById(foundToken.userId());
        user = userMapper.updateDtoIsEmailActive(user, true);
        userCommandService.save(user);
        tokenCommandService.deleteByUserId(foundToken.userId());
        return "Success";
    }
}
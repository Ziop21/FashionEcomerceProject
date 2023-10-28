package project.fashionecommerce.backend.fashionecommerceproject.service.authentication;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.fashionecommerce.backend.fashionecommerceproject.dto.authentication.MyAuthentication;
import project.fashionecommerce.backend.fashionecommerceproject.dto.token.Token;
import project.fashionecommerce.backend.fashionecommerceproject.dto.user.UserId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.user.UserMapper;
import project.fashionecommerce.backend.fashionecommerceproject.dto.user.User;
import project.fashionecommerce.backend.fashionecommerceproject.exception.TokenRefreshException;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.token.TokenCommandService;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.token.TokenQueryService;
import project.fashionecommerce.backend.fashionecommerceproject.util.JwtUtils;
import project.fashionecommerce.backend.fashionecommerceproject.config.security.userDetails.Implement.UserDetailsImpl;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.user.UserCommandService;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.user.UserQueryService;

import java.util.List;
import java.util.stream.Collectors;

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
    public List<String> getMyRoles(String username) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return securityContext.getAuthentication().getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
    }

    @Transactional
    public String refreshToken(String refreshToken) {
        return tokenQueryService.findByToken(refreshToken)
                .map(tokenCommandService::verifyExpiration)
                .map(Token::userId)
                .map(userId -> {
                    User user = userQueryService.findById(new UserId(userId));
                    ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(user, "/api");
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
        ResponseCookie usernameCookie = ResponseCookie.from("username", null).path("/api").build();
        ResponseCookie jwtCookie = jwtUtils.getCleanJwtCookie();
        ResponseCookie jwtRefreshCookie = jwtUtils.getCleanJwtRefreshCookie();

        return new MyAuthentication(null, null, usernameCookie.toString(), jwtCookie.toString(), jwtRefreshCookie.toString());
    }

}

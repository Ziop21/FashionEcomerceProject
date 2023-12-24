package project.fashionecommerce.backend.fashionecommerceproject.service.guest;

import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.fashionecommerce.backend.fashionecommerceproject.dto.authen.MyAuthentication;
import project.fashionecommerce.backend.fashionecommerceproject.dto.cart.Cart;
import project.fashionecommerce.backend.fashionecommerceproject.exception.MyForbiddenException;
import project.fashionecommerce.backend.fashionecommerceproject.config.security.userDetails.Impl.UserDetailsImpl;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.cart.CartCommandService;
import project.fashionecommerce.backend.fashionecommerceproject.util.JwtUtils;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GuestUseCaseService {
    @NonNull final JwtUtils jwtUtils;
    @NonNull final CartCommandService cartCommandService;

    @Value("${fashion_ecommerce.app.jwtCartCookieName}")
    private String cartTokenCookieName;

    public String getCurrentUser(){
        return Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getPrincipal).get().toString();
    }

    @Transactional
    public MyAuthentication index(HttpServletRequest request) {
        String currentUser = getCurrentUser();
        if (currentUser == "anonymousUser") {
            String oldCartToken = jwtUtils.getCookieValueByName(request, cartTokenCookieName);
            if (oldCartToken == null || jwtUtils.validateJwtToken(oldCartToken) == false) {
                Cart cart = Cart.builder().isDeleted(false).isActive(true).build();
                cart = cartCommandService.save(cart);
                String cartToken = jwtUtils.generateTokenFromCartId(cart.id());
//                ResponseCookie cartTokenCookie = jwtUtils.generateCookie(cartTokenCookieName, cartToken, "/api");
//                return MyAuthentication.builder().cartTokenCookieString(cartTokenCookie.toString()).build();
            }
            return MyAuthentication.builder().build();
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String oldCartToken = jwtUtils.getCookieValueByName(request, cartTokenCookieName);
        if (oldCartToken == null){
            Cart cart = Cart.builder().isDeleted(false).isActive(true).build();
            cart = cartCommandService.save(cart);
            String cartToken = jwtUtils.generateTokenFromCartId(cart.id());
//            ResponseCookie cartTokenCookie = jwtUtils.generateCookie(cartTokenCookieName, cartToken, "/api");
//            return MyAuthentication.builder().userDetails(userDetails).cartTokenCookieString(cartTokenCookie.toString()).build();
        }
        if (oldCartToken == null || jwtUtils.validateJwtToken(oldCartToken) == false) {
            throw new MyForbiddenException();
        }
        return MyAuthentication.builder().userDetails(userDetails).build();
    }



}

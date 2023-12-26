package project.fashionecommerce.backend.fashionecommerceproject.service.guest;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.cart.CartCommandService;
import project.fashionecommerce.backend.fashionecommerceproject.util.JwtUtils;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GuestUseCaseService {
    @NonNull final JwtUtils jwtUtils;
    @NonNull final CartCommandService cartCommandService;

    public String getCurrentUser(){
        return Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getPrincipal).get().toString();
    }
}

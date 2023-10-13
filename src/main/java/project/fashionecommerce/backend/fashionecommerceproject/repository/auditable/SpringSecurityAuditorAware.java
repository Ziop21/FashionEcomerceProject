package project.fashionecommerce.backend.fashionecommerceproject.repository.auditable;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import project.fashionecommerce.backend.fashionecommerceproject.repository.security.user_detail.UserDetailsImpl;
import java.util.Optional;

@Component
public class SpringSecurityAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        Optional<?> currentUser = Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getPrincipal);
        if (currentUser.toString() != "anonymousUser")
            return Optional.ofNullable(currentUser.toString());
        return Optional.ofNullable(currentUser
                    .map(UserDetailsImpl.class::cast)
                    .get().getId());
    }
}

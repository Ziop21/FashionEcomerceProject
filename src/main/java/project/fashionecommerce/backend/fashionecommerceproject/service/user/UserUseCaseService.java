package project.fashionecommerce.backend.fashionecommerceproject.service.user;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.fashionecommerce.backend.fashionecommerceproject.dto.user.User;

@Service
@RequiredArgsConstructor
public class UserUseCaseService {
    @NonNull final UserQueryService userQueryService;
    public User findById(String userId) {
       return userQueryService.findById(userId);
    }
}

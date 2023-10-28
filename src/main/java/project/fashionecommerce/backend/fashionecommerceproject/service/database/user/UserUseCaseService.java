package project.fashionecommerce.backend.fashionecommerceproject.service.database.user;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.fashionecommerce.backend.fashionecommerceproject.dto.user.User;
import project.fashionecommerce.backend.fashionecommerceproject.dto.user.UserId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.user.level.UserQuery;

@Service
@RequiredArgsConstructor
public class UserUseCaseService {
    @NonNull final UserCommandService userCommandService;
    @NonNull final UserQueryService userQueryService;
    @Transactional
    public void update(UserId userId, User user) {
        userCommandService.update(userId, user);
    }
    @Transactional
    public void delete(UserId userId) {
        userCommandService.delete(userId);
    }
    @Transactional
    public void save(User user) {
        userCommandService.save(user);
    }

    public Page<User> findAll(UserQuery userQuery, PageRequest pageRequest) {
        return userQueryService.findAll(userQuery, pageRequest);
    }

    public User findById(UserId userId) {
        return userQueryService.findById(userId);
    }
}

package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.user;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.user.models.UserModelMapper;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.user.models.UserRequest;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.user.models.UserResponse;
import project.fashionecommerce.backend.fashionecommerceproject.dto.user.User;
import project.fashionecommerce.backend.fashionecommerceproject.dto.user.UserId;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.user.UserUseCaseService;

@RestController
@RequiredArgsConstructor
public class UserController implements UserAPI {
    @NonNull final UserUseCaseService userUseCaseService;
    @NonNull final UserModelMapper userModelMapper;
    @Override
    public ResponseEntity<UserResponse> findById(String userId) {
        User user = userUseCaseService.findById(new UserId(userId));
        return new ResponseEntity<>(userModelMapper.toModel(user), HttpStatus.OK);
    }

    @Override
    public void delete(String userId) {
        userUseCaseService.delete(new UserId(userId));
    }

    @Override
    public void update(String userId, UserRequest userRequest) {
        User user = userModelMapper.toDto(userRequest);
        userUseCaseService.update(new UserId(userId), user);
    }
}

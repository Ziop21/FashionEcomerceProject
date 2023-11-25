package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.user.level;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.user.level.models.UserLevelModelMapper;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.user.level.models.UserLevelRequest;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.user.level.models.UserLevelResponse;
import project.fashionecommerce.backend.fashionecommerceproject.dto.user.level.UserLevel;
import project.fashionecommerce.backend.fashionecommerceproject.dto.user.level.UserLevelId;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.user.level.UserLevelUseCaseService;

@RestController
@RequiredArgsConstructor
public class UserLevelController implements UserLevelAPI {
    @NonNull final UserLevelUseCaseService userLevelUseCaseService;
    @NonNull final UserLevelModelMapper userLevelModelMapper;
    @Override
    public ResponseEntity<UserLevelResponse> findById(String userLevelId) {
        UserLevel userLevel = userLevelUseCaseService.findById(new UserLevelId(userLevelId));
        UserLevelResponse userLevelResponse = userLevelModelMapper.toModel(userLevel);
        return new ResponseEntity<>(userLevelResponse, HttpStatus.OK);
    }

    @Override
    public void delete(String userLevelId) {
        userLevelUseCaseService.delete(new UserLevelId(userLevelId));
    }
    @Override
    public void update(String userLevelId, UserLevelRequest userLevelRequest) {
        UserLevel userLevel = userLevelModelMapper.toDto(userLevelRequest);
        userLevelUseCaseService.update(new UserLevelId(userLevelId), userLevel);
    }

}

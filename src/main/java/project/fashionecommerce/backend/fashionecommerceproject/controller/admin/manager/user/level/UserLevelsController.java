package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.user.level;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import project.fashionecommerce.backend.fashionecommerceproject.common.models.PageResponse;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.user.level.models.UserLevelModelMapper;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.user.level.models.UserLevelRequest;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.user.level.models.UserLevelResponse;
import project.fashionecommerce.backend.fashionecommerceproject.dto.handler.MySortHandler;
import project.fashionecommerce.backend.fashionecommerceproject.dto.user.level.UserLevel;
import project.fashionecommerce.backend.fashionecommerceproject.dto.user.level.UserLevelQuery;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.user.level.UserLevelUseCaseService;

@RestController
@RequiredArgsConstructor
public class UserLevelsController implements UserLevelsAPI {
    @NonNull final UserLevelUseCaseService userLevelUseCaseService;
    @NonNull final UserLevelModelMapper userLevelModelMapper;
    @Override
    public void save(UserLevelRequest userLevelRequest) {
        UserLevel userLevel = userLevelModelMapper.toDto(userLevelRequest);
        userLevelUseCaseService.save(userLevel);
    }

    @Override
    public ResponseEntity<PageResponse<UserLevelResponse>> findAll(String search, String sort, Integer pageCurrent, Integer pageUserLevel) {
        UserLevelQuery userLevelQuery = new UserLevelQuery(search);
        PageRequest pageRequest = PageRequest.of(pageCurrent - 1, pageUserLevel, MySortHandler.of(sort));

        Page<UserLevel> userLevelPage = userLevelUseCaseService.findAll(userLevelQuery, pageRequest);

        PageResponse<UserLevelResponse> userLevelResponsePage = new PageResponse<>(userLevelPage.map(userLevelModelMapper::toModel));
        return new ResponseEntity<>(userLevelResponsePage, HttpStatus.OK);
    }
}

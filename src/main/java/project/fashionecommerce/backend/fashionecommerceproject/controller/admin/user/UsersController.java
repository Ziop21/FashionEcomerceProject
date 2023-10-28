package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.user;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import project.fashionecommerce.backend.fashionecommerceproject.common.models.PageResponse;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.user.models.UserModelMapper;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.user.models.UserRequest;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.user.models.UserResponse;
import project.fashionecommerce.backend.fashionecommerceproject.dto.handler.MySortHandler;
import project.fashionecommerce.backend.fashionecommerceproject.dto.user.User;
import project.fashionecommerce.backend.fashionecommerceproject.dto.user.level.UserQuery;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.user.UserUseCaseService;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UsersController implements UsersAPI {
    @NonNull final UserUseCaseService userUseCaseService;
    @NonNull final UserModelMapper userModelMapper;
    @Override
    public void save(UserRequest userRequest) {
        User user = userModelMapper.toDto(userRequest);
        userUseCaseService.save(user);
    }

    @Override
    public ResponseEntity<PageResponse<UserResponse>> findAll(String search, Boolean isEmailActive,
                                                              Boolean isDeleted, Boolean isPhoneActive,
                                                              List<String> userLevelIds,
                                                              LocalDate fromDate,
                                                              LocalDate toDate,
                                                              String sort, Integer pageCurrent, Integer pageSize) {
        UserQuery userQuery = UserQuery.builder()
                .search(search)
                .isDeleted(isDeleted)
                .isPhoneActive(isPhoneActive)
                .isEmailActive(isEmailActive)
                .userLevelIds(userLevelIds)
                .fromDate(fromDate)
                .toDate(toDate)
                .build();

        PageRequest pageRequest = PageRequest.of(pageCurrent - 1, pageSize, MySortHandler.of(sort));

        Page<User> userPage = userUseCaseService.findAll(userQuery, pageRequest);

        PageResponse<UserResponse> userResponsePageResponse = new PageResponse<>(userPage.map(userModelMapper::toModel));

        return new ResponseEntity<>(userResponsePageResponse, HttpStatus.OK);
    }
}

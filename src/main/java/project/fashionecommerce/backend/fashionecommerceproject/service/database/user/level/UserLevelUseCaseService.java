package project.fashionecommerce.backend.fashionecommerceproject.service.database.user.level;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.fashionecommerce.backend.fashionecommerceproject.dto.user.level.UserLevel;
import project.fashionecommerce.backend.fashionecommerceproject.dto.user.level.UserLevelId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.user.level.UserLevelQuery;

@Service
@RequiredArgsConstructor
public class UserLevelUseCaseService {
    @NonNull final UserLevelCommandService userLevelCommandService;
    @NonNull final UserLevelQueryService userLevelQueryService;
    @Transactional
    public void update(UserLevelId userLevelId, UserLevel userLevel) {
        userLevelCommandService.update(userLevelId, userLevel);
    }
    @Transactional
    public void delete(UserLevelId userLevelId) {
        userLevelCommandService.delete(userLevelId);
    }
    @Transactional
    public void save(UserLevel userLevel) {
        userLevelCommandService.save(userLevel);
    }

    public Page<UserLevel> findAll(UserLevelQuery userLevelQuery, PageRequest pageRequest) {
        return userLevelQueryService.findAll(userLevelQuery, pageRequest);
    }

    public UserLevel findById(UserLevelId userLevelId) {
        return userLevelQueryService.findById(userLevelId);
    }
}

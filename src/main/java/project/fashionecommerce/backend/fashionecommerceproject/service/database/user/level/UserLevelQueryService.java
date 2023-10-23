package project.fashionecommerce.backend.fashionecommerceproject.service.database.user.level;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import project.fashionecommerce.backend.fashionecommerceproject.dto.user.level.UserLevel;
import project.fashionecommerce.backend.fashionecommerceproject.dto.user.level.UserLevelId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.user.level.UserLevelMapper;
import project.fashionecommerce.backend.fashionecommerceproject.dto.user.level.UserLevelQuery;
import project.fashionecommerce.backend.fashionecommerceproject.exception.MyResourceNotFoundException;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.user.level.UserLevelEntity;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.user.level.UserLevelRepository;

@Service
@RequiredArgsConstructor
public class UserLevelQueryService {
    @NonNull final UserLevelRepository userLevelRepository;
    @NonNull final UserLevelMapper userLevelMapper;
    public Page<UserLevel> findAll(UserLevelQuery userLevelQuery, PageRequest pageRequest) {
        Page<UserLevelEntity> userLevelEntityPage = userLevelRepository.customFindAll(userLevelQuery.search(), pageRequest);
        return userLevelEntityPage.map(userLevelMapper::toDto);
    }

    public UserLevel findById(UserLevelId userLevelId) {
        UserLevelEntity userLevelEntity = userLevelRepository.findById(userLevelId.id())
                .orElseThrow(MyResourceNotFoundException::new);
        return userLevelMapper.toDto(userLevelEntity);
    }
}

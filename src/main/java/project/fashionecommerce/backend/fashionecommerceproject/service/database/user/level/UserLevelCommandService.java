package project.fashionecommerce.backend.fashionecommerceproject.service.database.user.level;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.fashionecommerce.backend.fashionecommerceproject.dto.user.level.UserLevel;
import project.fashionecommerce.backend.fashionecommerceproject.dto.user.level.UserLevelId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.user.level.UserLevelMapper;
import project.fashionecommerce.backend.fashionecommerceproject.exception.MyConflictsException;
import project.fashionecommerce.backend.fashionecommerceproject.exception.MyResourceNotFoundException;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.user.level.UserLevelEntity;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.user.level.UserLevelRepository;

@Service
@RequiredArgsConstructor
public class UserLevelCommandService {
    @NonNull final UserLevelRepository userLevelRepository;
    @NonNull final UserLevelMapper userLevelMapper;
    @NonNull final
    public void save(UserLevel userLevel) {
        if (userLevelRepository.existsByName(userLevel.name()))
            throw new MyConflictsException();
        UserLevelEntity userLevelEntity = userLevelMapper.toEntity(userLevel);
        userLevelRepository.save(userLevelEntity);
    }

    public void delete(UserLevelId userLevelId) {
        userLevelRepository.deleteById(userLevelId.id());
    }

    public void update(UserLevelId userLevelId, UserLevel userLevel) {
        UserLevelEntity foundEntity = userLevelRepository.findById(userLevelId.id()).orElseThrow(MyResourceNotFoundException::new);
        if (!userLevel.name().equals(foundEntity.getName())
                && userLevelRepository.existsByName(userLevel.name()))
            throw new MyConflictsException();
        userLevelMapper.updateExisted(userLevel, foundEntity);
        userLevelRepository.save(foundEntity);
    }
}

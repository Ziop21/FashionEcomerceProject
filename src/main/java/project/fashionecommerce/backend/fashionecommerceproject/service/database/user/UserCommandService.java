package project.fashionecommerce.backend.fashionecommerceproject.service.database.user;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.fashionecommerce.backend.fashionecommerceproject.dto.user.User;
import project.fashionecommerce.backend.fashionecommerceproject.dto.user.UserId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.user.UserMapper;
import project.fashionecommerce.backend.fashionecommerceproject.exception.MyConflictsException;
import project.fashionecommerce.backend.fashionecommerceproject.exception.MyResourceNotFoundException;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.user.UserEntity;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.user.UserRepository;

@Service
@RequiredArgsConstructor
public class UserCommandService {
    @NonNull final UserRepository userRepository;
    @NonNull final UserMapper userMapper;

    public User save(User user){
        UserEntity userEntity = userMapper.toEntity(user);
        if (user.isDeleted() == false && userRepository.existsByEmailAndIsDeleted(user.email(), false))
            throw new MyConflictsException();
        userRepository.save(userEntity);
        return userMapper.toDto(userEntity);
    }

    public void delete(UserId userId) {
        userRepository.deleteById(userId.id());
    }

    public void update(UserId userId, User user) {
        UserEntity foundEntity = userRepository.findById(userId.id())
                .orElseThrow(MyResourceNotFoundException::new);
        if (user.isDeleted() == false){
            UserEntity entity = userRepository.findByEmailAndIsDeleted(user.email(), false)
                    .orElseThrow(MyResourceNotFoundException::new);
            if (!userId.id().equals(entity.getId()))
                throw new MyConflictsException();
        }
        userMapper.updateExisted(user, foundEntity);
        userRepository.save(foundEntity);
    }

    public void updateHashedPassword(UserId userId, String hashedPassword){
        UserEntity userEntity = userRepository.findById(userId.id())
                .orElseThrow(MyResourceNotFoundException::new);
        userEntity.setHashedPassword(hashedPassword);
        userRepository.save(userEntity);
    }

    public void updateIsEmailActive(UserId userId, boolean isEmailActive) {
        UserEntity userEntity = userRepository.findById(userId.id())
                .orElseThrow(MyResourceNotFoundException::new);
        userEntity.setIsEmailActive(isEmailActive);
        userRepository.save(userEntity);
    }
}

package project.fashionecommerce.backend.fashionecommerceproject.service.database.user;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    final PasswordEncoder passwordEncoder;
    public User save(User user){
        UserEntity userEntity = userMapper.toEntity(user);
        if (user.password() != null){
            String hashedPassword = passwordEncoder.encode(user.password());
            userEntity.setHashedPassword(hashedPassword);
        }
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
        if (user.password() != null && user.password() != ""){
            String hashedPassword = passwordEncoder.encode(user.password());
            foundEntity.setHashedPassword(hashedPassword);
        }
        if (user.isDeleted() == false){
            try {
                UserEntity entity = userRepository.findByEmailAndIsDeleted(user.email(), false)
                        .orElseThrow(MyResourceNotFoundException::new);
                if (!userId.id().equals(entity.getId()))
                    throw new MyConflictsException();
            } catch (MyResourceNotFoundException e) {
                userMapper.updateExisted(user, foundEntity);
                userRepository.save(foundEntity);
            }
        }
        userMapper.updateExisted(user, foundEntity);
        userRepository.save(foundEntity);
    }

    public void updateHashedPasswordAndIsActive(UserId userId, String hashedPassword, Boolean isActive){
        UserEntity userEntity = userRepository.findById(userId.id())
                .orElseThrow(MyResourceNotFoundException::new);
        userEntity.setHashedPassword(hashedPassword);
        userEntity.setIsActive(isActive);
        userRepository.save(userEntity);
    }

    public void updateIsEmailActive(UserId userId, Boolean isEmailActive) {
        UserEntity userEntity = userRepository.findById(userId.id())
                .orElseThrow(MyResourceNotFoundException::new);
        userEntity.setIsEmailActive(isEmailActive);
        userRepository.save(userEntity);
    }
}

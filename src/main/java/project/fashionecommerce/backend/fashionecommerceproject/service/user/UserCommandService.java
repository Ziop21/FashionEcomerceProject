package project.fashionecommerce.backend.fashionecommerceproject.service.user;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.fashionecommerce.backend.fashionecommerceproject.dto.user.User;
import project.fashionecommerce.backend.fashionecommerceproject.dto.user.UserMapper;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.user.UserEntity;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.user.UserRepository;

@Service
@RequiredArgsConstructor
public class UserCommandService {
    @NonNull final UserRepository userRepository;
    @NonNull final UserMapper userMapper;

    public User save(User user){
        UserEntity userEntity = userMapper.toEntity(user);
        userRepository.save(userEntity);
        return userMapper.toDto(userEntity);
    }
}

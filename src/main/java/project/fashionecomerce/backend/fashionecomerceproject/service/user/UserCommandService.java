package project.fashionecomerce.backend.fashionecomerceproject.service.user;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.fashionecomerce.backend.fashionecomerceproject.dto.user.User;
import project.fashionecomerce.backend.fashionecomerceproject.dto.user.UserMapper;
import project.fashionecomerce.backend.fashionecomerceproject.repository.user.UserEntity;
import project.fashionecomerce.backend.fashionecomerceproject.repository.user.UserRepository;

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

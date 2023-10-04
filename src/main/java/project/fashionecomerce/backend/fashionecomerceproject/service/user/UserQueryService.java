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
public class UserQueryService {
    @NonNull final UserRepository userRepository;
    @NonNull final UserMapper userMapper;
    public Boolean existsByEmail(String email){
        return userRepository.existsByEmail(email);
    }

    public User findByEmail(String email) {
        UserEntity user = userRepository.findByEmail(email);
        return userMapper.toDto(user);
    }
}

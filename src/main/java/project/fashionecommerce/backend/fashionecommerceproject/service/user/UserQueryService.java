package project.fashionecommerce.backend.fashionecommerceproject.service.user;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.fashionecommerce.backend.fashionecommerceproject.dto.user.User;
import project.fashionecommerce.backend.fashionecommerceproject.dto.user.UserMapper;
import project.fashionecommerce.backend.fashionecommerceproject.exception.MyResourceNotFoundException;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.token.TokenRepository;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.user.UserEntity;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.user.UserRepository;

@Service
@RequiredArgsConstructor
public class UserQueryService {
    @NonNull final UserRepository userRepository;
    @NonNull final UserMapper userMapper;
    private final TokenRepository tokenRepository;

    public Boolean existsByEmail(String email){
        if (!userRepository.existsByEmail(email))
            return false;

        UserEntity userEntity = userRepository.findByEmail(email).orElseThrow(MyResourceNotFoundException::new);
        if (!userEntity.getIsEmailActive()){
            userRepository.deleteById(userEntity.getId());
            tokenRepository.deleteByUserId(userEntity.getId());
            return false;
        }
        return true;
    }

    public User findByEmail(String email) {
        UserEntity user = userRepository.findByEmail(email).orElseThrow(MyResourceNotFoundException::new);
        return userMapper.toDto(user);
    }

    public User findById(String userId) {
        UserEntity user = userRepository.findById(userId).orElseThrow(MyResourceNotFoundException::new);
        return userMapper.toDto(user);
    }
}

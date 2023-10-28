package project.fashionecommerce.backend.fashionecommerceproject.service.database.token;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.fashionecommerce.backend.fashionecommerceproject.dto.token.Token;
import project.fashionecommerce.backend.fashionecommerceproject.dto.token.TokenMapper;
import project.fashionecommerce.backend.fashionecommerceproject.exception.TokenRefreshException;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.token.TokenEntity;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.token.TokenRepository;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenCommandService {
    @NonNull final TokenRepository tokenRepository;
    @NonNull final TokenMapper tokenMapper;

    public void deleteByUserId(String userId) {
        tokenRepository.deleteByUserId(userId);
    }
    public Token verifyExpiration(Token token) {
        if (token.expiryDateTime().compareTo(LocalDateTime.now()) < 0) {
            tokenRepository.delete(tokenMapper.toEntity(token));
            throw new TokenRefreshException(token.token(), "Refresh token was expired. Please make a new signin request");
        }
        return token;
    }
    public Token save(String userId, Long durationS) {
        if (tokenRepository.existsByUserId(userId))
            tokenRepository.deleteByUserId(userId);

        TokenEntity tokenEntity = new TokenEntity();

        tokenEntity.setUserId(userId);
        tokenEntity.setExpiryDateTime(LocalDateTime.now().plusSeconds(durationS));
        tokenEntity.setToken(UUID.randomUUID().toString());

        tokenEntity = tokenRepository.save(tokenEntity);
        return tokenMapper.toDto(tokenEntity);
    }
}

package project.fashionecommerce.backend.fashionecommerceproject.service.database.token;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.fashionecommerce.backend.fashionecommerceproject.dto.token.Token;
import project.fashionecommerce.backend.fashionecommerceproject.dto.token.TokenMapper;
import project.fashionecommerce.backend.fashionecommerceproject.exception.MyResourceNotFoundException;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.token.TokenEntity;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.token.TokenRepository;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenQueryService {
    @NonNull final TokenRepository tokenRepository;
    @NonNull final TokenMapper tokenMapper;
    public Optional<Token> findByToken(String token) {
        TokenEntity refreshTokenEntity = tokenRepository.findByToken(token)
                .orElseThrow(MyResourceNotFoundException::new);
        return Optional.ofNullable(tokenMapper.toDto(refreshTokenEntity));
    }
}

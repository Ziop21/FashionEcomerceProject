package project.fashionecomerce.backend.fashionecomerceproject.service.verification.token;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.fashionecomerce.backend.fashionecomerceproject.dto.user.UserMapper;
import project.fashionecomerce.backend.fashionecomerceproject.dto.verification.token.VerificationToken;
import project.fashionecomerce.backend.fashionecomerceproject.dto.verification.token.VerificationTokenMapper;
import project.fashionecomerce.backend.fashionecomerceproject.repository.verification.token.VerificationTokenEntity;
import project.fashionecomerce.backend.fashionecomerceproject.repository.verification.token.VerificationTokenRepository;

@Service
@RequiredArgsConstructor
public class VerificationTokenCommandService {
    @NonNull final VerificationTokenRepository verificationTokenRepository;
    @NonNull final VerificationTokenMapper verificationTokenMapper;
    @NonNull final UserMapper userMapper;

    public VerificationToken save(VerificationToken token) {
        VerificationTokenEntity verificationTokenEntity = new VerificationTokenEntity(token.userId());
        verificationTokenRepository.save(verificationTokenEntity);
        return verificationTokenMapper.toDto(verificationTokenEntity);
    }
}

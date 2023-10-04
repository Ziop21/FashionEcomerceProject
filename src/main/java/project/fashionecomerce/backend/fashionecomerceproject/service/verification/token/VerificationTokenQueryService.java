package project.fashionecomerce.backend.fashionecomerceproject.service.verification.token;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.fashionecomerce.backend.fashionecomerceproject.dto.verification.token.VerificationToken;
import project.fashionecomerce.backend.fashionecomerceproject.dto.verification.token.VerificationTokenMapper;
import project.fashionecomerce.backend.fashionecomerceproject.exception.MyResourceNotFoundException;
import project.fashionecomerce.backend.fashionecomerceproject.repository.verification.token.VerificationTokenEntity;
import project.fashionecomerce.backend.fashionecomerceproject.repository.verification.token.VerificationTokenRepository;

@Service
@RequiredArgsConstructor
public class VerificationTokenQueryService {
    @NonNull
    final VerificationTokenRepository verificationTokenRepository;
    @NonNull
    final VerificationTokenMapper verificationTokenMapper;

    public VerificationToken findByUserId(String id) {
        VerificationTokenEntity verificationTokenEntity = verificationTokenRepository.findByUserId(id);
        if (verificationTokenEntity.equals(null))
            throw new MyResourceNotFoundException();
        return verificationTokenMapper.toDto(verificationTokenEntity);
    }
}

package project.fashionecomerce.backend.fashionecomerceproject.dto.verification.token;

import org.mapstruct.Mapper;
import project.fashionecomerce.backend.fashionecomerceproject.repository.verification.token.VerificationTokenEntity;

@Mapper(componentModel = "spring")
public interface VerificationTokenMapper {
    VerificationTokenEntity toEntity(VerificationToken token);

    VerificationToken toDto(VerificationTokenEntity verificationTokenEntity);
}

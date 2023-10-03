package project.fashionecomerce.backend.fashionecomerceproject.repository.verification.token;

import org.springframework.data.mongodb.repository.MongoRepository;
import project.fashionecomerce.backend.fashionecomerceproject.repository.verification.token.VerificationTokenEntity;

public interface VerificationTokenRepository extends MongoRepository<VerificationTokenEntity, String> {
    VerificationTokenEntity findByUserId(String id);
}

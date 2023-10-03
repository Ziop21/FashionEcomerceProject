package project.fashionecomerce.backend.fashionecomerceproject.repository.verification.token;

import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import project.fashionecomerce.backend.fashionecomerceproject.dto.user.User;
import project.fashionecomerce.backend.fashionecomerceproject.dto.verification.token.VerificationToken;
import project.fashionecomerce.backend.fashionecomerceproject.repository.user.UserEntity;
import java.util.UUID;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@EqualsAndHashCode()
@Data
@Document(collection = "verification_token")
public class VerificationTokenEntity {
    @Id
    private String id;
    private String token;
    @DBRef
    private String userId;
    private LocalDateTime expiryDateTime;
    private static final int EXPIRATION = 30;
    public VerificationTokenEntity(String userId){
        UUID uniqueId = UUID.randomUUID();
        token = uniqueId.toString();
        userId = userId;
        expiryDateTime = calculateExpiryDateTime(EXPIRATION);
    }
    private LocalDateTime calculateExpiryDateTime(int expiryTimeInMinutes) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime newDateTime = currentDateTime.plus(expiryTimeInMinutes, ChronoUnit.MINUTES);
        return newDateTime;
    }
}

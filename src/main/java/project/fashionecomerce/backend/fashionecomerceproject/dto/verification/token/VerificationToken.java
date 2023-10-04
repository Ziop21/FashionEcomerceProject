package project.fashionecomerce.backend.fashionecomerceproject.dto.verification.token;

import lombok.Builder;
import project.fashionecomerce.backend.fashionecomerceproject.dto.user.User;
import java.time.LocalDateTime;
@Builder
public record VerificationToken(
    String id,
    String token,
    String userId,
    LocalDateTime expiryDateTime
) {
}

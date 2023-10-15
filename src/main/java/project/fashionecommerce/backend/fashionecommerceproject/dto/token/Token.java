package project.fashionecommerce.backend.fashionecommerceproject.dto.token;

import java.time.LocalDateTime;

public record Token(
    String id,
    String userId,
    String token,
    LocalDateTime expiryDateTime,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    String createdBy,
    String updatedBy
) {
}

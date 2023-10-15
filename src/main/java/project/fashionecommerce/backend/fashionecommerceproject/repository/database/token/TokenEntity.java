package project.fashionecommerce.backend.fashionecommerceproject.repository.database.token;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import project.fashionecommerce.backend.fashionecommerceproject.repository.auditable.FullAuditable;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document("token")
public class TokenEntity extends FullAuditable implements Serializable {
    @Id
    private String id;
    private String userId;
    private String token;
    private LocalDateTime expiryDateTime;
}
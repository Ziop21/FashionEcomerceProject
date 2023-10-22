package project.fashionecommerce.backend.fashionecommerceproject.repository.database.user.level;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import project.fashionecommerce.backend.fashionecommerceproject.repository.auditable.FullAuditable;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode()
@Document("user_level")
public class UserLevelEntity extends FullAuditable implements Serializable {
    private String id;
    private String name;
    private String description;
    private Long minPoint;
    private Double discount;
    private Boolean isDeleted;
}

package project.fashionecommerce.backend.fashionecommerceproject.repository.database.role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import project.fashionecommerce.backend.fashionecommerceproject.dto.enums.ERole;
import project.fashionecommerce.backend.fashionecommerceproject.repository.auditable.FullAuditable;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode()
public class RoleEntity extends FullAuditable implements Serializable {
    private ERole name;
}

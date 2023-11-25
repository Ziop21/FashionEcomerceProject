package project.fashionecommerce.backend.fashionecommerceproject.repository.database.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import project.fashionecommerce.backend.fashionecommerceproject.repository.auditable.FullAuditable;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.role.RoleEntity;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode()
@Document(value = "user")
public class UserEntity extends FullAuditable implements Serializable {
    @Id
    private String id;
    private List<RoleEntity> roles;
    private String firstName;
    private String lastName;
    private String slug;
    private String idCard;
    private String email;
    private List<String> phones;
    private String hashedPassword;
    private Boolean isEmailActive = false;
    private Boolean isPhoneActive = false;
    private List<String> addresses;
    private String avatar;
    private Long point;
    private String eWallet;
    @Field(targetType = FieldType.OBJECT_ID)
    private String userLevelId;
}
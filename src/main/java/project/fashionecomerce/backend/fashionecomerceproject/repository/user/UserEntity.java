package project.fashionecomerce.backend.fashionecomerceproject.repository.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode()
@Document(value = "User")
public class UserEntity {
    @Id
    private String id;
    private String role;
    private String firstName;
    private String lastName;
    private String slug;
    private String idCard;
    private String email;
    private List<String> phone;
    private String hashedPassword;
    private Boolean isEmailActive = false;
    private Boolean isPhoneActive = false;
    private List<String> address;
    private String avatar;
    private Long point;
    private String eWallet;
    private String userLevelId;
    private Boolean isDeleted = false;
}
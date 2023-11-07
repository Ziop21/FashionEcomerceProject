package project.fashionecommerce.backend.fashionecommerceproject.dto.user;

import lombok.Builder;
import project.fashionecommerce.backend.fashionecommerceproject.dto.role.Role;
import java.time.LocalDateTime;
import java.util.List;

@Builder
public record User (
        String id,
        List<Role> roles,
        String firstName,
        String lastName,
        String slug,
        String idCard,
        String email,
        List<String> phones,
        String hashedPassword,
        Boolean isEmailActive,
        Boolean isPhoneActive,
        List<String> addresses,
        String avatar,
        Long point,
        String eWallet,
        String userLevelId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        String createdBy,
        String updatedBy,
        Boolean isDeleted,
        Boolean isActive,
        String userLevelName
){
}

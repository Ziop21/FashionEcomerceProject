package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.user.models;

import project.fashionecommerce.backend.fashionecommerceproject.dto.role.Role;
import java.time.LocalDateTime;
import java.util.List;

public record UserResponse(
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
        Boolean isDeleted,
        Boolean isActive,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        String createdBy,
        String updatedBy
){
}

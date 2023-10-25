package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.user.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import project.fashionecommerce.backend.fashionecommerceproject.dto.role.Role;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record UserRequest(
        @NotNull
        List<Role> roles,
        String firstName,
        String lastName,
        String slug,
        String idCard,
        @NotNull
        @Email
        String email,
        List<String> phone,
        @NotNull
        String hashedPassword,
        @NotNull
        Boolean isEmailActive,
        Boolean isPhoneActive,
        List<String> address,
        String avatar,
        Long point,
        String eWallet,
        String userLevelId,
        @NotNull
        Boolean isDeleted
){
}

package project.fashionecomerce.backend.fashionecomerceproject.dto.user;

import java.util.List;

public record User (
        String id,
        String role,
        String firstName,
        String lastName,
        String slug,
        String idCard,
        String email,
        List<String> phone,
        String hashedPassword,
        Boolean isEmailActive,
        Boolean isPhoneActive,
        List<String> address,
        String avatar,
        Long point,
        String eWallet,
        String userLevelId,
        Boolean isDeleted

){
}

package project.fashionecommerce.backend.fashionecommerceproject.controller.customer.user.models;

import java.util.List;

public record CustomerUserResponse(
        String firstName,
        String lastName,
        String idCard,
        String email,
        List<String> phones,
        List<String> addresses,
        String avatar,
        Long point,
        String eWallet,
        String userLevelId,
        String userLevelName
) {
}

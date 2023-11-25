package project.fashionecommerce.backend.fashionecommerceproject.controller.customer.user.models;

import java.util.List;

public record CustomerUserResponse(
        String id,
        String firstName,
        String lastName,
        String idCard,
        String email,
        List<String> phone,
        List<String> address,
        String avatar,
        Long point,
        String eWallet,
        String userLevelId,
        String userLevelName
) {
}

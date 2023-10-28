package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.follow.models;

import jakarta.validation.constraints.NotNull;

public record FollowRequest(
        @NotNull
        String productId,
        @NotNull
        String userId,
        @NotNull
        Boolean isDeleted,

        @NotNull
        Boolean isActive
) {
}

package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.review.models;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ReviewRequest(
        @NotNull
        String orderId,
        String content,
        @NotNull
        Long rating,
        List<String> images,
        @NotNull
        Boolean isDeleted,

        @NotNull
        Boolean isActive
) {
}

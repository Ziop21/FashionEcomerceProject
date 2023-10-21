package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.delivery.models;

import java.time.LocalDateTime;

public record DeliveryResponse(
        String id,
        String name,
        String description,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        String createdBy,
        String updatedBy,
        Boolean isDeleted,
        Boolean isActive
) {
}

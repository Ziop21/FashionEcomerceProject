package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.delivery.models;

import jakarta.validation.constraints.NotNull;

public record DeliveryRequest(
        @NotNull
        String name,
        String description
) {
}

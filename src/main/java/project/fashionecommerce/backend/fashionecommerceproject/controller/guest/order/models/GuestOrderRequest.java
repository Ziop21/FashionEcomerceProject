package project.fashionecommerce.backend.fashionecommerceproject.controller.guest.order.models;

import jakarta.validation.constraints.NotNull;

public record GuestOrderRequest(
        @NotNull
        String address,
        @NotNull
        String phone
) {
}

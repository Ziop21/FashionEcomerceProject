package project.fashionecommerce.backend.fashionecommerceproject.controller.guest.order.models;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record GuestOrderRequest(
        @NotNull
        String address,
        @NotNull
        @Pattern(regexp = "^0[0-9]{9}$", message = "Is not a phone number")
        @Size(min = 10, max = 10, message = "Is not a phone number")
        String phone,
        @NotNull
        Double shippingFee,
        @NotNull
        String deliveryId,
        @NotNull
        Boolean isPaidBefore
) {
}

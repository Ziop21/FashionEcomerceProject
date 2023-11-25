package project.fashionecommerce.backend.fashionecommerceproject.controller.customer.order.models;

import project.fashionecommerce.backend.fashionecommerceproject.dto.enums.EOrderStatus;
import java.time.LocalDateTime;

public record CustomerOrderResponse(
        String id,
        String address,
        String phone,
        String deliveryId,
        String deliveryName,
        Double shippingFee,
        EOrderStatus status,
        Boolean isPaidBefore,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}

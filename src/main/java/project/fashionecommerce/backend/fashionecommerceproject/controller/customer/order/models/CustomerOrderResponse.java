package project.fashionecommerce.backend.fashionecommerceproject.controller.customer.order.models;

import project.fashionecommerce.backend.fashionecommerceproject.controller.customer.order.items.models.CustomerOrderItemResponse;
import project.fashionecommerce.backend.fashionecommerceproject.dto.enums.EOrderStatus;
import java.time.LocalDateTime;
import java.util.List;

public record CustomerOrderResponse(
        String id,
        String address,
        String username,
        String phone,
        String deliveryId,
        String deliveryName,
        Double shippingFee,
        EOrderStatus status,
        Boolean isPaidBefore,
        List<CustomerOrderItemResponse> orderItems,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}

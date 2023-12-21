package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.order.models;

import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.order.items.models.OrderItemResponse;
import project.fashionecommerce.backend.fashionecommerceproject.dto.enums.EOrderStatus;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(
        String id,
        String userId,
        String username,
        String address,
        String phone,
        List<OrderItemResponse> orderItems,
        String deliveryId,
        Double shippingFee,
        EOrderStatus status,
        Boolean isPaidBefore,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Boolean isDeleted,
        Boolean isActive
) {
}

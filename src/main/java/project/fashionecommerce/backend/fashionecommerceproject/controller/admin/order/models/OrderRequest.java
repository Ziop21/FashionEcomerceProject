package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.order.models;

import jakarta.validation.constraints.NotNull;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.order.items.models.OrderItemRequest;
import project.fashionecommerce.backend.fashionecommerceproject.dto.enums.EOrderStatus;
import java.util.List;

public record OrderRequest(
        @NotNull
        String userId,
        @NotNull
        String address,
        @NotNull
        String phone,
        @NotNull
        List<OrderItemRequest> orderItems,
        @NotNull
        String deliveryId,
        @NotNull
        Double shippingFee,
        EOrderStatus status,
        Boolean isPaidBefore,
        @NotNull
        Boolean isDeleted,

        @NotNull
        Boolean isActive
) {
}

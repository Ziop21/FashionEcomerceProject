package project.fashionecommerce.backend.fashionecommerceproject.service.customer;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import project.fashionecommerce.backend.fashionecommerceproject.config.security.userDetails.Impl.UserDetailsImpl;
import project.fashionecommerce.backend.fashionecommerceproject.dto.delivery.Delivery;
import project.fashionecommerce.backend.fashionecommerceproject.dto.delivery.DeliveryId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.enums.ERole;
import project.fashionecommerce.backend.fashionecommerceproject.dto.order.Order;
import project.fashionecommerce.backend.fashionecommerceproject.dto.order.OrderId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.order.OrderMapper;
import project.fashionecommerce.backend.fashionecommerceproject.dto.order.OrderQuery;
import project.fashionecommerce.backend.fashionecommerceproject.exception.MyForbiddenException;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.delivery.DeliveryQueryService;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.order.OrderQueryService;

@Service
@RequiredArgsConstructor
public class CustomerOrderUseCaseService {
    @NonNull final OrderQueryService orderQueryService;
    @NonNull final DeliveryQueryService deliveryQueryService;
    @NonNull final OrderMapper orderMapper;
    public Order findById(OrderId orderId) {
        Order foundOrder = orderQueryService.findById(orderId);
        if (!foundOrder.isActive() || foundOrder.isDeleted())
            throw new MyForbiddenException();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        if (!userDetails.getId().equals(foundOrder.userId())){
            throw new MyForbiddenException();
        }

        Delivery delivery = deliveryQueryService.findById(new DeliveryId(foundOrder.deliveryId()));
        foundOrder = orderMapper.updateDtoDeliveryName(foundOrder, delivery.name());
        return foundOrder;
    }

    public Page<Order> findAll(OrderQuery orderQuery, PageRequest pageRequest) {
        Page<Order> orderPage = orderQueryService.findAll(orderQuery, pageRequest, ERole.CUSTOMER);
        orderPage = orderPage.map(order -> {
           Delivery delivery = deliveryQueryService.findById(new DeliveryId(order.deliveryId()));
           order = orderMapper.updateDtoDeliveryName(order, delivery.name());
           return order;
        });
        return orderPage;
    }
}

package project.fashionecommerce.backend.fashionecommerceproject.service.database.order;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.fashionecommerce.backend.fashionecommerceproject.dto.enums.EOrderStatus;
import project.fashionecommerce.backend.fashionecommerceproject.dto.order.Order;
import project.fashionecommerce.backend.fashionecommerceproject.dto.order.OrderMapper;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.order.OrderEntity;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.order.OrderRepository;

@Service
@RequiredArgsConstructor
public class OrderCommandService {
    @NonNull final OrderRepository orderRepository;
    @NonNull final OrderMapper orderMapper;
    public void save(Order order) {
        OrderEntity orderEntity = orderMapper.toEntity(order);
        if (order.status() == null)
            orderEntity.setStatus(EOrderStatus.WAITING);
        if (order.isPaidBefore() == null)
            orderEntity.setIsPaidBefore(false);
        orderRepository.save(orderEntity);
    }
}

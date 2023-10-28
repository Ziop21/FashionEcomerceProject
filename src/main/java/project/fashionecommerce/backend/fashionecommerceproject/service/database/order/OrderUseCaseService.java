package project.fashionecommerce.backend.fashionecommerceproject.service.database.order;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.fashionecommerce.backend.fashionecommerceproject.dto.order.Order;
import project.fashionecommerce.backend.fashionecommerceproject.dto.order.OrderId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.order.OrderQuery;

@Service
@RequiredArgsConstructor
public class OrderUseCaseService {
    @NonNull final OrderCommandService orderCommandService;
    @NonNull final OrderQueryService orderQueryService;
    @Transactional
    public void update(OrderId orderId, Order order) {
        orderCommandService.update(orderId, order);
    }
    @Transactional
    public void delete(OrderId orderId) {
        orderCommandService.delete(orderId);
    }
    @Transactional
    public void save(Order order) {
        orderCommandService.save(order);
    }

    public Page<Order> findAll(OrderQuery orderQuery, PageRequest pageRequest) {
        return orderQueryService.findAll(orderQuery, pageRequest);
    }
    public Order findById(OrderId orderId) {
        return orderQueryService.findById(orderId);
    }

}

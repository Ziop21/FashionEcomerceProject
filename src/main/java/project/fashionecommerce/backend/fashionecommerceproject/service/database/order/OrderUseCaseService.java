package project.fashionecommerce.backend.fashionecommerceproject.service.database.order;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import project.fashionecommerce.backend.fashionecommerceproject.dto.order.Order;
import project.fashionecommerce.backend.fashionecommerceproject.dto.order.OrderQuery;

@Service
@RequiredArgsConstructor
public class OrderUseCaseService {
    @NonNull final OrderCommandService orderCommandService;
    @NonNull final OrderQueryService orderQueryService;
    public void save(Order order) {
        orderCommandService.save(order);
    }

    public Page<Order> findAll(OrderQuery orderQuery, PageRequest pageRequest) {
        return orderQueryService.findAll(orderQuery, pageRequest);
    }
}

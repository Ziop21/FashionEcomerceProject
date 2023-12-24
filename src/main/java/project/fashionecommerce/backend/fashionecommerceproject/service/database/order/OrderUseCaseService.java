package project.fashionecommerce.backend.fashionecommerceproject.service.database.order;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.fashionecommerce.backend.fashionecommerceproject.dto.enums.EOrderStatus;
import project.fashionecommerce.backend.fashionecommerceproject.dto.enums.ERole;
import project.fashionecommerce.backend.fashionecommerceproject.dto.order.Order;
import project.fashionecommerce.backend.fashionecommerceproject.dto.order.OrderId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.order.OrderQuery;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.Stock;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.StockId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.StockMapper;
import project.fashionecommerce.backend.fashionecommerceproject.exception.OutOfQuantityException;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.stock.StockCommandService;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.stock.StockQueryService;

@Service
@RequiredArgsConstructor
public class OrderUseCaseService {
    @NonNull final OrderCommandService orderCommandService;
    @NonNull final StockCommandService stockCommandService;

    @NonNull final OrderQueryService orderQueryService;
    @NonNull final StockQueryService stockQueryService;

    @NonNull final StockMapper stockMapper;

    @Transactional
    public void update(OrderId orderId, Order newOrder) {
        Order oldOrder = orderQueryService.findById(orderId);
        if (oldOrder.status().equals(EOrderStatus.WAITING) && newOrder.status().equals(EOrderStatus.SHIPPING)){
            oldOrder.orderItems().forEach((item) -> {
                Stock foundStock = stockQueryService.findById(new StockId(item.stockId()));
                if (foundStock.quantity() < item.quantity())
                    throw new OutOfQuantityException();
                foundStock = stockMapper.updateDtoQuantity(foundStock, foundStock.quantity() - item.quantity());
                stockCommandService.update(new StockId(foundStock.id()), foundStock);
            });
        }

        orderCommandService.update(orderId, newOrder);
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
        return orderQueryService.findAll(orderQuery, pageRequest , ERole.ADMIN);
    }
    public Order findById(OrderId orderId) {
        return orderQueryService.findById(orderId);
    }

}

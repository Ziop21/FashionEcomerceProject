package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.order;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.order.models.OrderModelMapper;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.order.models.OrderRequest;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.order.models.OrderResponse;
import project.fashionecommerce.backend.fashionecommerceproject.dto.order.Order;
import project.fashionecommerce.backend.fashionecommerceproject.dto.order.OrderId;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.order.OrderUseCaseService;

@RestController
@RequiredArgsConstructor
public class OrderController implements OrderAPI{
    @NonNull
    final OrderUseCaseService orderUseCaseService;
    @NonNull
    final OrderModelMapper orderModelMapper;
    @Override
    public void update(String orderId, OrderRequest orderRequest) {
        Order order = orderModelMapper.toDto(orderRequest);
        orderUseCaseService.update(new OrderId(orderId), order);
    }

    @Override
    public ResponseEntity<OrderResponse> findById(String orderId) {
        Order order = orderUseCaseService.findById(new OrderId(orderId));
        OrderResponse orderResponse = orderModelMapper.toModel(order);
        return new ResponseEntity<>(orderResponse, HttpStatus.OK);
    }

    @Override
    public void delete(String orderId) {
        orderUseCaseService.delete(new OrderId(orderId));
    }
}

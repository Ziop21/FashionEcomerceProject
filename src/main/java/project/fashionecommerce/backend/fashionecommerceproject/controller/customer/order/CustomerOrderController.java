package project.fashionecommerce.backend.fashionecommerceproject.controller.customer.order;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import project.fashionecommerce.backend.fashionecommerceproject.common.models.PageResponse;
import project.fashionecommerce.backend.fashionecommerceproject.controller.customer.order.models.CustomerOrderModelMapper;
import project.fashionecommerce.backend.fashionecommerceproject.controller.customer.order.models.CustomerOrderResponse;
import project.fashionecommerce.backend.fashionecommerceproject.dto.enums.EOrderStatus;
import project.fashionecommerce.backend.fashionecommerceproject.dto.handler.MySortHandler;
import project.fashionecommerce.backend.fashionecommerceproject.dto.order.Order;
import project.fashionecommerce.backend.fashionecommerceproject.dto.order.OrderId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.order.OrderQuery;
import project.fashionecommerce.backend.fashionecommerceproject.service.customer.CustomerOrderUseCaseService;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CustomerOrderController implements CustomerOrderAPI {
    @NonNull final CustomerOrderUseCaseService customerOrderUseCaseService;
    @NonNull final CustomerOrderModelMapper customerOrderModelMapper;
    @Override
    public ResponseEntity<CustomerOrderResponse> findById(String orderId) {
        Order order = customerOrderUseCaseService.findById(new OrderId(orderId));
        return new ResponseEntity<>(customerOrderModelMapper.toModel(order), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PageResponse<CustomerOrderResponse>> findAll(String search, List<String> deliveryIds, List<EOrderStatus> statuses, Boolean isPaidBefore, LocalDate fromDate, LocalDate toDate, String sort, Integer pageCurrent, Integer pageSize) {
        OrderQuery orderQuery = OrderQuery.builder()
                .search(search)
                .deliveryIds(deliveryIds)
                .statuses(statuses)
                .isPaidBefore(isPaidBefore)
                .fromDate(fromDate)
                .toDate(toDate)
                .build();

        PageRequest pageRequest = PageRequest.of(pageCurrent-1, pageSize, MySortHandler.of(sort));

        Page<Order> orderPage = customerOrderUseCaseService.findAll(orderQuery, pageRequest);

        PageResponse<CustomerOrderResponse> finalOrders = new PageResponse<>(orderPage.map(customerOrderModelMapper::toModel));

        return new ResponseEntity<>(finalOrders, HttpStatus.OK);
    }
}

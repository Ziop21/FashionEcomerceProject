package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.order;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import project.fashionecommerce.backend.fashionecommerceproject.common.models.PageResponse;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.order.models.OrderModelMapper;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.order.models.OrderRequest;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.order.models.OrderResponse;
import project.fashionecommerce.backend.fashionecommerceproject.dto.enums.EOrderStatus;
import project.fashionecommerce.backend.fashionecommerceproject.dto.handler.MySortHandler;
import project.fashionecommerce.backend.fashionecommerceproject.dto.order.Order;
import project.fashionecommerce.backend.fashionecommerceproject.dto.order.OrderQuery;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.order.OrderUseCaseService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrdersController implements OrdersAPI{
    @NonNull final OrderUseCaseService orderUseCaseService;
    @NonNull final OrderModelMapper orderModelMapper;
    @Override
    public void save(OrderRequest orderRequest) {
        Order order = orderModelMapper.toDto(orderRequest);
        orderUseCaseService.save(order);
    }

    @Override
    public ResponseEntity<PageResponse<OrderResponse>> findAll(String search, List<String> deliveryIds,
                                                               List<EOrderStatus> statuses,
                                                               Boolean isPaidBefore, LocalDate fromDate,
                                                               LocalDate toDate, String sort,
                                                               Integer currentPage, Integer pageSize) {
        OrderQuery orderQuery = OrderQuery.builder()
                .search(search)
                .deliveryIds(deliveryIds)
                .statuses(statuses)
                .isPaidBefore(isPaidBefore)
                .fromDate(fromDate)
                .toDate(toDate)
                .build();

        PageRequest pageRequest = PageRequest.of(currentPage-1, pageSize, MySortHandler.of(sort));

        Page<Order> orderPage = orderUseCaseService.findAll(orderQuery, pageRequest);

        PageResponse<OrderResponse> orderResponsePageResponse = new PageResponse<>(orderPage.map(orderModelMapper::toModel));

        return new ResponseEntity<>(orderResponsePageResponse, HttpStatus.OK);
    }
}

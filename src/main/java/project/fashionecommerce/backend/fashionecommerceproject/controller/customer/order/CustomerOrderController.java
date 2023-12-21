package project.fashionecommerce.backend.fashionecommerceproject.controller.customer.order;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import project.fashionecommerce.backend.fashionecommerceproject.common.models.PageResponse;
import project.fashionecommerce.backend.fashionecommerceproject.controller.customer.order.items.models.CustomerOrderItemResponse;
import project.fashionecommerce.backend.fashionecommerceproject.controller.customer.order.models.CustomerOrderModelMapper;
import project.fashionecommerce.backend.fashionecommerceproject.controller.customer.order.models.CustomerOrderResponse;
import project.fashionecommerce.backend.fashionecommerceproject.dto.color.Color;
import project.fashionecommerce.backend.fashionecommerceproject.dto.color.ColorId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.enums.EOrderStatus;
import project.fashionecommerce.backend.fashionecommerceproject.dto.handler.MySortHandler;
import project.fashionecommerce.backend.fashionecommerceproject.dto.order.Order;
import project.fashionecommerce.backend.fashionecommerceproject.dto.order.OrderId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.order.OrderQuery;
import project.fashionecommerce.backend.fashionecommerceproject.dto.product.Product;
import project.fashionecommerce.backend.fashionecommerceproject.dto.product.ProductId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.size.Size;
import project.fashionecommerce.backend.fashionecommerceproject.dto.size.SizeId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.Stock;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.StockId;
import project.fashionecommerce.backend.fashionecommerceproject.service.customer.CustomerOrderUseCaseService;
import project.fashionecommerce.backend.fashionecommerceproject.service.guest.GuestColorUseCaseService;
import project.fashionecommerce.backend.fashionecommerceproject.service.guest.GuestProductUseCaseService;
import project.fashionecommerce.backend.fashionecommerceproject.service.guest.GuestSizeUseCaseService;
import project.fashionecommerce.backend.fashionecommerceproject.service.guest.GuestStockUseCaseService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class CustomerOrderController implements CustomerOrderAPI {
    @NonNull final CustomerOrderUseCaseService customerOrderUseCaseService;
    @NonNull final CustomerOrderModelMapper customerOrderModelMapper;
    @NonNull final GuestProductUseCaseService guestProductUseCaseService;
    @NonNull final GuestColorUseCaseService guestColorUseCaseService;
    @NonNull final GuestSizeUseCaseService guestSizeUseCaseService;
    @NonNull final GuestStockUseCaseService guestStockUseCaseService;
    @Override
    public ResponseEntity<CustomerOrderResponse> findById(String orderId) {
        Order order = customerOrderUseCaseService.findById(new OrderId(orderId));
        return new ResponseEntity<>(customerOrderModelMapper.toModel(order), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PageResponse<CustomerOrderResponse>> findAll(String search, List<String> deliveryIds, List<EOrderStatus> statuses, Boolean isPaidBefore, LocalDate fromDate, LocalDate toDate, String sort, Integer currentPage, Integer pageSize) {
        OrderQuery orderQuery = OrderQuery.builder()
                .search(search)
                .deliveryIds(deliveryIds)
                .statuses(statuses)
                .isPaidBefore(isPaidBefore)
                .fromDate(fromDate)
                .toDate(toDate)
                .build();

        PageRequest pageRequest = PageRequest.of(currentPage-1, pageSize, MySortHandler.of(sort));

        Page<Order> orderPage = customerOrderUseCaseService.findAll(orderQuery, pageRequest);


        PageResponse<CustomerOrderResponse> finalOrders = new PageResponse<>(orderPage.map((order -> {
            CustomerOrderResponse orderResponse = customerOrderModelMapper.toModel(order);
            List<CustomerOrderItemResponse> orderItemResponses = orderResponse.orderItems();
            orderItemResponses = orderItemResponses.stream().map((orderItem) -> {
                Stock stock = guestStockUseCaseService.findById(new StockId(orderItem.stockId()));
                Product product = guestProductUseCaseService.findById(new ProductId(stock.productId()));
                orderItem = customerOrderModelMapper.updateModelProduct(orderItem, product.id(), product.name(), product.price());
                Size size = guestSizeUseCaseService.findById(new SizeId(stock.sizeId()));
                orderItem = customerOrderModelMapper.updateModelSize(orderItem, size.id() ,size.name());
                Color color = guestColorUseCaseService.findById(new ColorId(stock.colorId()));
                orderItem = customerOrderModelMapper.updateModelColor(orderItem, color.id() ,color.name());
                return orderItem;
            }).collect(Collectors.toList());
            return customerOrderModelMapper.updateModelOrderItems(order, orderItemResponses);
        })));

        return new ResponseEntity<>(finalOrders, HttpStatus.OK);
    }
}

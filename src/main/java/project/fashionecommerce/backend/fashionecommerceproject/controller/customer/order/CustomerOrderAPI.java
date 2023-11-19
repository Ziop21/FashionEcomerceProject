package project.fashionecommerce.backend.fashionecommerceproject.controller.customer.order;

import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.fashionecommerce.backend.fashionecommerceproject.common.models.PageResponse;
import project.fashionecommerce.backend.fashionecommerceproject.controller.customer.order.models.CustomerOrderResponse;
import project.fashionecommerce.backend.fashionecommerceproject.dto.enums.EOrderStatus;

import java.time.LocalDate;
import java.util.List;

@RequestMapping("/api/customer/order")
public interface CustomerOrderAPI {
    @GetMapping("/{orderId}")
    ResponseEntity<CustomerOrderResponse> findById(@PathVariable String orderId);

    @GetMapping
    ResponseEntity<PageResponse<CustomerOrderResponse>> findAll(
            @RequestParam(required = false, value = "search", defaultValue = "") String search,
            @RequestParam(required = false, value = "deliveryIds", defaultValue = "") List<String> deliveryIds,
            @RequestParam(required = false, value = "statuses", defaultValue = "") List<EOrderStatus> statuses,
            @RequestParam(required = false, value = "isPaidBefore", defaultValue = "") Boolean isPaidBefore,
            @RequestParam(required = false, value = "fromDate", defaultValue = "") LocalDate fromDate,
            @RequestParam(required = false, value = "toDate", defaultValue = "") LocalDate toDate,
            @RequestParam(required = false, value = "sort", defaultValue = "") String sort,
            @RequestParam(required = false, value = "currentPage", defaultValue = "1") @Min(1) Integer currentPage,
            @RequestParam(required = false, value = "pageSize", defaultValue = "20") Integer pageSize
    );
}

package project.fashionecommerce.backend.fashionecommerceproject.controller.customer.stock;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import project.fashionecommerce.backend.fashionecommerceproject.controller.customer.review.models.CustomerReviewRequest;

@RequestMapping("/api/customer/stock")
public interface CustomerStockAPI {
    @PutMapping("/{stockId}")
    void addReview(@PathVariable String stockId, @RequestBody @Valid CustomerReviewRequest customerReviewRequest);
}

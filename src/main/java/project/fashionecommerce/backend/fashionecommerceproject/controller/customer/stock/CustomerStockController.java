package project.fashionecommerce.backend.fashionecommerceproject.controller.customer.stock;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import project.fashionecommerce.backend.fashionecommerceproject.controller.customer.review.models.CustomerReviewModelMapper;
import project.fashionecommerce.backend.fashionecommerceproject.controller.customer.review.models.CustomerReviewRequest;
import project.fashionecommerce.backend.fashionecommerceproject.dto.review.Review;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.StockId;
import project.fashionecommerce.backend.fashionecommerceproject.service.customer.CustomerStockUseCaseService;

@RestController
@RequiredArgsConstructor
public class CustomerStockController implements CustomerStockAPI {
    @NonNull final CustomerReviewModelMapper customerReviewModelMapper;
    @NonNull final CustomerStockUseCaseService customerStockUseCaseService;
    @Override
    public void addReview(String stockId, CustomerReviewRequest customerReviewRequest) {
        Review review = customerReviewModelMapper.toDto(customerReviewRequest);
        customerStockUseCaseService.addReview(new StockId(stockId), review);
    }
}

package project.fashionecommerce.backend.fashionecommerceproject.service.customer;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.fashionecommerce.backend.fashionecommerceproject.dto.enums.EOrderStatus;
import project.fashionecommerce.backend.fashionecommerceproject.dto.order.Order;
import project.fashionecommerce.backend.fashionecommerceproject.dto.order.OrderId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.product.Product;
import project.fashionecommerce.backend.fashionecommerceproject.dto.product.ProductId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.product.ProductMapper;
import project.fashionecommerce.backend.fashionecommerceproject.dto.review.Review;
import project.fashionecommerce.backend.fashionecommerceproject.dto.review.ReviewMapper;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.Stock;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.StockId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.StockMapper;
import project.fashionecommerce.backend.fashionecommerceproject.dto.user.User;
import project.fashionecommerce.backend.fashionecommerceproject.dto.user.UserId;
import project.fashionecommerce.backend.fashionecommerceproject.exception.MyConflictsException;
import project.fashionecommerce.backend.fashionecommerceproject.exception.MyForbiddenException;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.order.OrderQueryService;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.product.ProductCommmandService;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.product.ProductQueryService;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.stock.StockCommandService;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.stock.StockQueryService;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.user.UserQueryService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerStockUseCaseService {
    @NonNull final StockCommandService stockCommandService;
    @NonNull final ProductCommmandService productCommmandService;

    @NonNull final OrderQueryService orderQueryService;
    @NonNull final StockQueryService stockQueryService;
    @NonNull final ProductQueryService productQueryService;
    @NonNull final UserQueryService userQueryService;

    @NonNull final StockMapper stockMapper;
    @NonNull final ReviewMapper reviewMapper;
    @NonNull final ProductMapper productMapper;

    @Transactional
    public void addReview(StockId stockId, Review review) {
        Order order = orderQueryService.findById(new OrderId(review.orderId()));
        if (order.status() != EOrderStatus.COMPLETED || order.isDeleted() || !order.isActive())
            throw new MyForbiddenException();

        Stock foundStock = stockQueryService.findById(stockId);
        if (!foundStock.reviews().isEmpty()) {
            Review finalReview = review;
            foundStock.reviews().forEach((item) -> {
                if (item.orderId().equals(finalReview.orderId()))
                    throw new MyConflictsException();
            });
        }

        review = reviewMapper.updateDtoIsActive(review, true);
        review = reviewMapper.updateDtoIsDeleted(review, false);

        List<Review> foundReviews = foundStock.reviews();
        foundReviews.add(review);

        foundStock = stockMapper.updateDtoReviews(foundStock, foundReviews);

        Product foundProduct = productQueryService.findById(new ProductId(foundStock.productId()));
        List<Stock> stocksByProductId = stockQueryService.findAllByProductId(new ProductId(foundProduct.id()));
        Long countReview = 0l;
        for (int i = 0; i < stocksByProductId.size(); i++) {
            countReview = countReview + stocksByProductId.get(i).reviews().size();
        }

        Long weight = 1l;
        User foundUser = userQueryService.findById(new UserId(order.userId()));
        if (foundUser.point() <= 10000)
            weight = 1l;
        else if (10000 < foundUser.point() && foundUser.point() <= 20000)
            weight = 2l;
        else if (20000 < foundUser.point())
            weight = 3l;

        Double newRating = foundProduct.rating() + (weight*review.rating() - foundProduct.rating())/(countReview + 1);

        foundProduct = productMapper.updateDtoRating(foundProduct, newRating);
        productCommmandService.update(new ProductId(foundProduct.id()), foundProduct);

        stockCommandService.update(stockId, foundStock);
    }
}

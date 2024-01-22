package project.fashionecommerce.backend.fashionecommerceproject.service.guest;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import project.fashionecommerce.backend.fashionecommerceproject.dto.color.ColorId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.enums.ERole;
import project.fashionecommerce.backend.fashionecommerceproject.dto.order.Order;
import project.fashionecommerce.backend.fashionecommerceproject.dto.order.OrderId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.product.ProductId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.review.Review;
import project.fashionecommerce.backend.fashionecommerceproject.dto.review.ReviewMapper;
import project.fashionecommerce.backend.fashionecommerceproject.dto.size.SizeId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.Stock;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.StockId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.StockMapper;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.StockQuery;
import project.fashionecommerce.backend.fashionecommerceproject.dto.user.User;
import project.fashionecommerce.backend.fashionecommerceproject.dto.user.UserId;
import project.fashionecommerce.backend.fashionecommerceproject.exception.MyResourceNotFoundException;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.order.OrderQueryService;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.stock.StockQueryService;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.user.UserQueryService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GuestStockUseCaseService {
    @NonNull
    final StockMapper stockMapper;
    @NonNull
    final UserQueryService userQueryService;
    @NonNull
    final StockQueryService stockQueryService;
    @NonNull
    final OrderQueryService orderQueryService;
    @NonNull
    final ReviewMapper reviewMapper;
    public Stock findById(StockId stockId){
        Stock stock = stockQueryService.findById(stockId);
        if (stock.isDeleted() || !stock.isActive())
            throw new MyResourceNotFoundException();
        List<Review> foundReviews = stock.reviews();
        for (int i = 0 ; i < foundReviews.size() ; i++) {
            Review item = foundReviews.get(i);
            if (item.isDeleted())
                foundReviews.remove(i);
            if (!item.isActive())
                continue;
            Order foundOrder = orderQueryService.findById(new OrderId(item.orderId()));
            if (!foundOrder.isActive() || foundOrder.isDeleted()){
                foundReviews.remove(i);
                continue;
            }
            User foundUser = userQueryService.findById(new UserId(foundOrder.userId()));
            if (foundUser.isDeleted() || !foundUser.isActive()){
                foundReviews.remove(i);
                continue;
            }
            item = reviewMapper.updateDtoUser(item, foundUser.id(), foundUser.firstName() + " " + foundUser.lastName());
            foundReviews.remove(i);
            foundReviews.add(i, item);
        }
        stock = stockMapper.updateDtoReviews(stock, foundReviews);
        return stock;
    }
    public Page<Stock> findAllByProductId(StockQuery query, PageRequest pageRequest) {
        Page<Stock> stocks = stockQueryService.findAll(query, pageRequest, ERole.GUEST);
        return stocks;
    }

    public Stock findByProductIdSizeIdColorId(ProductId productId, SizeId sizeId, ColorId colorId) {
        Stock stock = stockQueryService.findByProductIdSizeIdColorId(productId, sizeId, colorId);
        if (!stock.isActive() || stock.isDeleted())
            throw new MyResourceNotFoundException();
        return stock;
    }
}

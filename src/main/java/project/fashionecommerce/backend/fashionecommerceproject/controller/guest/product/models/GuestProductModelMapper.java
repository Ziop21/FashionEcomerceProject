package project.fashionecommerce.backend.fashionecommerceproject.controller.guest.product.models;

import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import project.fashionecommerce.backend.fashionecommerceproject.controller.guest.color.models.GuestColorModelMapper;
import project.fashionecommerce.backend.fashionecommerceproject.controller.guest.color.models.GuestColorResponse;
import project.fashionecommerce.backend.fashionecommerceproject.controller.guest.review.models.GuestReviewModelMapper;
import project.fashionecommerce.backend.fashionecommerceproject.controller.guest.review.models.GuestReviewResponse;
import project.fashionecommerce.backend.fashionecommerceproject.controller.guest.size.models.GuestSizeModelMapper;
import project.fashionecommerce.backend.fashionecommerceproject.controller.guest.size.models.GuestSizeResponse;
import project.fashionecommerce.backend.fashionecommerceproject.dto.product.Product;
import project.fashionecommerce.backend.fashionecommerceproject.dto.review.Review;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.Stock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class GuestProductModelMapper {
    @Autowired
    GuestColorModelMapper guestColorModelMapper;
    @Autowired GuestSizeModelMapper guestSizeModelMapper;
    @Autowired GuestReviewModelMapper guestReviewModelMapper;

    public GuestProductResponse toModel(Product product){
        List<Stock> stocks = product.stocks();
        List<GuestReviewResponse> reviewResponses = new ArrayList<>();
        if (stocks != null){
            reviewResponses = stocks.stream().map(stock -> {
                        List<Review> reviews = stock.reviews();
                        List<GuestReviewResponse> tempResponse = reviews.stream()
                                .map(review -> {
                                    String colorName = product.colors().stream().filter(color -> color.id().equals(stock.colorId())).findFirst().get().name();
                                    String sizeName = product.sizes().stream().filter(size -> size.id().equals(stock.sizeId())).findFirst().get().name();
                                    return guestReviewModelMapper.toModel(review, colorName, sizeName);
                                })
                                .collect(Collectors.toList());
                        return tempResponse;
                    })
                    .flatMap(Collection::stream).collect(Collectors.toList());
        }
        List<GuestColorResponse> colors = product.colors() == null ? null : product.colors().stream().map(guestColorModelMapper::toModel).collect(Collectors.toList());
        List<GuestSizeResponse> sizes = product.sizes() == null ? null : product.sizes().stream().map(guestSizeModelMapper::toModel).collect(Collectors.toList());
        GuestProductResponse productResponse = GuestProductResponse.builder()
                .productId(product.id())
                .name(product.name())
                .description(product.description())
                .price(product.price())
                .promotionalPrice(product.promotionalPrice())
                .images(product.images())
                .rating(product.rating())
                .colors(colors)
                .sizes(sizes)
                .reviews(reviewResponses)
                .build();
        return productResponse;
    }
}

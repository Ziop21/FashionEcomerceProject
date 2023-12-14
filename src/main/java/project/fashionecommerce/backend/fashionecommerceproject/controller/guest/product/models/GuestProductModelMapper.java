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

    public abstract GuestProductResponse toModel(Product product);
}

package project.fashionecommerce.backend.fashionecommerceproject.controller.guest.product.models;

import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import project.fashionecommerce.backend.fashionecommerceproject.controller.guest.color.models.GuestColorModelMapper;
import project.fashionecommerce.backend.fashionecommerceproject.controller.guest.review.models.GuestReviewModelMapper;
import project.fashionecommerce.backend.fashionecommerceproject.controller.guest.size.models.GuestSizeModelMapper;
import project.fashionecommerce.backend.fashionecommerceproject.dto.product.Product;

@Mapper(componentModel = "spring")
public abstract class GuestProductModelMapper {
    @Autowired
    GuestColorModelMapper guestColorModelMapper;
    @Autowired GuestSizeModelMapper guestSizeModelMapper;
    @Autowired GuestReviewModelMapper guestReviewModelMapper;

    public abstract GuestProductResponse toModel(Product product);
}

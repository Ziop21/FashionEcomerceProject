package project.fashionecommerce.backend.fashionecommerceproject.controller.customer.review.models;

import org.mapstruct.Mapper;
import project.fashionecommerce.backend.fashionecommerceproject.dto.review.Review;

@Mapper(componentModel = "spring")
public interface CustomerReviewModelMapper {
    Review toDto(CustomerReviewRequest customerReviewRequest);
}

package project.fashionecommerce.backend.fashionecommerceproject.dto.review;

import org.mapstruct.Mapper;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.review.ReviewEntity;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    ReviewEntity toEntity(Review review);

    Review toDto(ReviewEntity reviewEntity);
}

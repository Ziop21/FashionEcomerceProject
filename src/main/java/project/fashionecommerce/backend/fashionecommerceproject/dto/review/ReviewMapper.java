package project.fashionecommerce.backend.fashionecommerceproject.dto.review;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.review.ReviewEntity;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    ReviewEntity toEntity(Review review);

    Review toDto(ReviewEntity reviewEntity);
    @Mapping(source = "username", target = "username")
    @Mapping(source = "userId", target = "userId")
    Review updateDto(Review review, String username, String userId);
}

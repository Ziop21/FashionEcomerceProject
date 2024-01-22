package project.fashionecommerce.backend.fashionecommerceproject.dto.review;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.review.ReviewEntity;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    ReviewEntity toEntity(Review review);

    Review toDto(ReviewEntity reviewEntity);

    @Mapping(source = "userId", target = "userId")
    @Mapping(source = "username", target = "username")
    Review updateDtoUser(Review review, String userId, String username);
    @Mapping(source = "isActive", target = "isActive")
    Review updateDtoIsActive(Review review, Boolean isActive);
    @Mapping(source = "isDeleted", target = "isDeleted")
    Review updateDtoIsDeleted(Review review, Boolean isDeleted);
}

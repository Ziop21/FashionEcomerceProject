package project.fashionecommerce.backend.fashionecommerceproject.dto.follow;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.follow.FollowEntity;

@Mapper(componentModel = "spring")
public interface FollowMapper {
    FollowEntity toEntity(Follow follow);

    @Mapping(source = "id", target = "id", ignore = true)
    @Mapping(source = "createdAt", target = "createdAt", ignore = true)
    void updateExisted(Follow follow, @MappingTarget FollowEntity foundEntity);

    Follow toDto(FollowEntity followEntity);

    @Mapping(source = "name", target = "productName")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "promotionalPrice", target = "promotionalPrice")
    @Mapping(source = "image", target = "image")
    Follow updateDto(Follow follow, String name, Double price, Double promotionalPrice, String image);

    @Mapping(source = "isActive", target = "isActive")
    @Mapping(source = "isDeleted", target = "isDeleted")
    Follow updateIsActiveAndIsDeleted(Follow foundDto, Boolean isActive, Boolean isDeleted);
}

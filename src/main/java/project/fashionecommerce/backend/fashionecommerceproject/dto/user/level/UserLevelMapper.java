package project.fashionecommerce.backend.fashionecommerceproject.dto.user.level;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.user.level.UserLevelEntity;

@Mapper(componentModel = "spring")
public interface UserLevelMapper {
    UserLevelEntity toEntity(UserLevel userLevel);

    UserLevel toDto(UserLevelEntity userLevelEntity);
    @Mapping(source = "id", target = "id", ignore = true)
    @Mapping(source = "createdAt", target = "createdAt", ignore = true)
    @Mapping(source = "createdBy", target = "createdBy", ignore = true)
    void updateExisted(UserLevel userLevel, @MappingTarget UserLevelEntity foundEntity);
}

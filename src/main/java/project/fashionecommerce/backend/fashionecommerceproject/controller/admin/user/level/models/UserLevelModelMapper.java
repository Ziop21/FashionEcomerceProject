package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.user.level.models;

import org.mapstruct.Mapper;
import project.fashionecommerce.backend.fashionecommerceproject.dto.user.level.UserLevel;

@Mapper(componentModel = "spring")
public interface UserLevelModelMapper {
    UserLevel toDto(UserLevelRequest userLevelRequest);

    UserLevelResponse toModel(UserLevel userLevel);
}

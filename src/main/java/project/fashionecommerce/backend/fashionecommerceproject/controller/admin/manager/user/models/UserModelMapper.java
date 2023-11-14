package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.user.models;

import org.mapstruct.Mapper;
import project.fashionecommerce.backend.fashionecommerceproject.dto.user.User;

@Mapper(componentModel = "spring")
public interface UserModelMapper {
    User toDto(UserRequest userRequest);

    UserResponse toModel(User user);
}

package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.follow.models;

import org.mapstruct.Mapper;
import project.fashionecommerce.backend.fashionecommerceproject.dto.follow.Follow;

@Mapper(componentModel = "spring")
public interface FollowModelMapper {
    Follow toDto(FollowRequest followRequest);

    FollowResponse toModel(Follow follow);
}

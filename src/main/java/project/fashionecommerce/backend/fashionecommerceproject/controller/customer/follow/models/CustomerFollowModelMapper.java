package project.fashionecommerce.backend.fashionecommerceproject.controller.customer.follow.models;

import org.mapstruct.Mapper;
import project.fashionecommerce.backend.fashionecommerceproject.dto.follow.Follow;

@Mapper(componentModel = "spring")
public interface CustomerFollowModelMapper {
    CustomerFollowResponse toModel(Follow follow);
}

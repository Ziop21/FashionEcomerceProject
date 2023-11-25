package project.fashionecommerce.backend.fashionecommerceproject.controller.customer.user.models;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import project.fashionecommerce.backend.fashionecommerceproject.dto.user.User;

@Mapper(componentModel = "spring")
public interface CustomerUserModelMapper {
    CustomerUserResponse toModel(User user);
    @Mapping(source = "confirmPassword", target = "hashedPassword")
    User toDto(CustomerUserRequest customerUserRequest);
}

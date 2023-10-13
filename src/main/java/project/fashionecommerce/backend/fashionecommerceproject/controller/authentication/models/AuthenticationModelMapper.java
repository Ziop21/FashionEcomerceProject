package project.fashionecommerce.backend.fashionecommerceproject.controller.authentication.models;

import org.mapstruct.Mapper;
import project.fashionecommerce.backend.fashionecommerceproject.dto.authentication.Login;
import project.fashionecommerce.backend.fashionecommerceproject.dto.authentication.Register;

@Mapper(componentModel = "spring")
public interface AuthenticationModelMapper {
    Login toDto(LoginRequest loginRequest);

    Register toDto(RegisterRequest registerRequest);
}

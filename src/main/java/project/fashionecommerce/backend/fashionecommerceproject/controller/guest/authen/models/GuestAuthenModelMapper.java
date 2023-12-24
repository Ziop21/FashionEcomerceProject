package project.fashionecommerce.backend.fashionecommerceproject.controller.guest.authen.models;

import org.mapstruct.Mapper;
import project.fashionecommerce.backend.fashionecommerceproject.dto.authen.Login;
import project.fashionecommerce.backend.fashionecommerceproject.dto.authen.Register;

@Mapper(componentModel = "spring")
public interface GuestAuthenModelMapper {
    Login toDto(LoginRequest loginRequest);

    Register toDto(RegisterRequest registerRequest);

    Register toDto(ChangePasswordRequest changePasswordRequest);
}

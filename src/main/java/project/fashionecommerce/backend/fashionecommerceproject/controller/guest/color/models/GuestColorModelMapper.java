package project.fashionecommerce.backend.fashionecommerceproject.controller.guest.color.models;

import org.mapstruct.Mapper;
import project.fashionecommerce.backend.fashionecommerceproject.dto.color.Color;

@Mapper(componentModel = "spring")
public interface GuestColorModelMapper {
    GuestColorResponse toModel(Color color);
}

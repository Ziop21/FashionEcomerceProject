package project.fashionecommerce.backend.fashionecommerceproject.controller.guest.size.models;

import org.mapstruct.Mapper;
import project.fashionecommerce.backend.fashionecommerceproject.dto.size.Size;

@Mapper(componentModel = "spring")
public interface GuestSizeModelMapper {
    GuestSizeResponse toModel(Size size);
}

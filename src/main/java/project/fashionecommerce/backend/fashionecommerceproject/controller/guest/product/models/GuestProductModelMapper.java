package project.fashionecommerce.backend.fashionecommerceproject.controller.guest.product.models;

import org.mapstruct.Mapper;
import project.fashionecommerce.backend.fashionecommerceproject.dto.product.Product;

@Mapper(componentModel = "spring")
public interface GuestProductModelMapper {
    GuestProductResponse toModel(Product product);
}

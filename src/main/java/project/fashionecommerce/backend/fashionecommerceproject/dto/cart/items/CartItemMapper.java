package project.fashionecommerce.backend.fashionecommerceproject.dto.cart.items;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartItemMapper {
    @Mapping(source = "productName", target = "productName")
    @Mapping(source = "colorName", target = "colorName")
    @Mapping(source = "sizeName", target = "sizeName")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "promotionalPrice", target = "promotionalPrice")
    CartItem updateDto(CartItem cartItem, String productName, String colorName,
                       String sizeName, Double price, Double promotionalPrice);
}

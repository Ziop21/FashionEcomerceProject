package project.fashionecommerce.backend.fashionecommerceproject.controller.guest.category.product.models;

import org.mapstruct.Mapper;
import project.fashionecommerce.backend.fashionecommerceproject.controller.guest.category.models.GuestCategoryResponse;
import project.fashionecommerce.backend.fashionecommerceproject.dto.category.product.CategoryProduct;

@Mapper(componentModel = "spring")
public interface GuestCategoryProductModelMapper {
    GuestCategoryProductModel toModel(CategoryProduct categoryProduct);
}

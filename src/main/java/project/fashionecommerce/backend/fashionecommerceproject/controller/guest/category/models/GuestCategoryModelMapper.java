package project.fashionecommerce.backend.fashionecommerceproject.controller.guest.category.models;

import org.mapstruct.Mapper;
import project.fashionecommerce.backend.fashionecommerceproject.dto.category.Category;

@Mapper(componentModel = "spring")
public interface GuestCategoryModelMapper {
    GuestCategoryResponse toModel(Category category);
}

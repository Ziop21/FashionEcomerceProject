package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.category.models;

import org.mapstruct.Mapper;
import project.fashionecommerce.backend.fashionecommerceproject.dto.category.Category;

@Mapper(componentModel = "spring")
public interface CategoryModelMapper {
    Category toDto(CategoryRequest categoryRequest);

    CategoryResponse toModel(Category category);
}

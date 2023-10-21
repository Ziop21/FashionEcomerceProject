package project.fashionecommerce.backend.fashionecommerceproject.dto.category;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.category.CategoryEntity;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryEntity toEntity(Category category);

    Category toDto(CategoryEntity categoryEntity);
    @Mapping(source = "id", target = "id", ignore = true)
    @Mapping(source = "createdBy", target = "createdBy", ignore = true)
    @Mapping(source = "createdAt", target = "createdAt", ignore = true)
    void updateExisted(Category category, @MappingTarget CategoryEntity categoryEntity);
}

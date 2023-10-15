package project.fashionecommerce.backend.fashionecommerceproject.dto.color;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.color.ColorEntity;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ColorMapper {

    ColorEntity toEntity(Color color);
    List<Color> toDto(List<ColorEntity> colorEntityList);
    Color toDto(ColorEntity colorEntity);
    @Mapping(source = "id", target = "id", ignore = true)
    @Mapping(source = "updatedAt", target = "updatedAt", ignore = true)
    @Mapping(source = "createdAt", target = "createdAt", ignore = true)
    void updateExist(Color color, @MappingTarget ColorEntity colorEntity);
}

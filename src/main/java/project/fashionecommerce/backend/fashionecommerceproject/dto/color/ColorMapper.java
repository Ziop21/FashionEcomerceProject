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
    @Mapping(source = "createdBy", target = "createdBy", ignore = true)
    @Mapping(source = "createdAt", target = "createdAt", ignore = true)
    void updateExisted(Color color, @MappingTarget ColorEntity colorEntity);
}

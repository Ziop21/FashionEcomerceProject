package project.fashionecomerce.backend.fashionecomerceproject.dto.color;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import project.fashionecomerce.backend.fashionecomerceproject.dto.size.Size;
import project.fashionecomerce.backend.fashionecomerceproject.repository.color.ColorEntity;
import project.fashionecomerce.backend.fashionecomerceproject.repository.size.SizeEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ColorMapper {

    ColorEntity toEntity(Color color);
    List<Color> toDto(List<ColorEntity> colorEntityList);
    Color toDto(ColorEntity colorEntity);
    @Mapping(source = "id", target = "id", ignore = true)
    void updateExist(Color color, @MappingTarget ColorEntity colorEntity);
}

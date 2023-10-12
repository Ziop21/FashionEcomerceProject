package project.fashionecomerce.backend.fashionecomerceproject.controller.color.models;

import org.mapstruct.Mapper;
import project.fashionecomerce.backend.fashionecomerceproject.dto.color.Color;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ColorModelMapper {

    Color toDto(ColorRequest colorRequest);
    List<ColorResponse> toModel(List<Color> colorList);
    ColorResponse toModel(Color color);
}

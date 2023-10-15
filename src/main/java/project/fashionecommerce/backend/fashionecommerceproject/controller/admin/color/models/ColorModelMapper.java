package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.color.models;

import org.mapstruct.Mapper;
import project.fashionecommerce.backend.fashionecommerceproject.dto.color.Color;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ColorModelMapper {

    Color toDto(ColorRequest colorRequest);
    List<ColorResponse> toModel(List<Color> colorList);
    ColorResponse toModel(Color color);
}

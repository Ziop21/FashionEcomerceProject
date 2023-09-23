package project.fashionecomerce.backend.fashionecomerceproject.controller.size.models;

import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import project.fashionecomerce.backend.fashionecomerceproject.dto.size.Size;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SizeModelMapper {
    Size toDto(SizeRequest sizeRequest);

    List<SizeResponse> toModel(List<Size> sizeList);

    SizeResponse toModel(Size size);
}

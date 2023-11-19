package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.size.models;

import org.mapstruct.Mapper;
import project.fashionecommerce.backend.fashionecommerceproject.dto.size.Size;
import java.util.List;

@Mapper(componentModel = "spring")
public interface SizeModelMapper {
    Size toDto(SizeRequest sizeRequest);

    List<SizeResponse> toModel(List<Size> sizeList);

    SizeResponse toModel(Size size);
}

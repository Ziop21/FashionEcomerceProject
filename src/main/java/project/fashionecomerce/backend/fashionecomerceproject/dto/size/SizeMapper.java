package project.fashionecomerce.backend.fashionecomerceproject.dto.size;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import project.fashionecomerce.backend.fashionecomerceproject.repository.size.SizeEntity;
import java.util.List;

@Mapper(componentModel = "spring")
public interface SizeMapper {
    SizeEntity toEntity(Size size);

    List<Size> toDto(List<SizeEntity> sizeEntityList);

    Size toDto(SizeEntity sizeEntity);
    @Mapping(source = "id", target = "id", ignore = true)
    void updateExist(Size size, @MappingTarget SizeEntity sizeEntity);
}

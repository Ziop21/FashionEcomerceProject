package project.fashionecommerce.backend.fashionecommerceproject.dto.size;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.size.SizeEntity;
import java.util.List;

@Mapper(componentModel = "spring")
public interface SizeMapper {
    SizeEntity toEntity(Size size);

    List<Size> toDto(List<SizeEntity> sizeEntityList);

    Size toDto(SizeEntity sizeEntity);
    @Mapping(source = "id", target = "id", ignore = true)
    @Mapping(source = "createdAt", target = "createdAt", ignore = true)
    @Mapping(source = "createdBy", target = "createdBy", ignore = true)
    void updateExist(Size size, @MappingTarget SizeEntity sizeEntity);
}

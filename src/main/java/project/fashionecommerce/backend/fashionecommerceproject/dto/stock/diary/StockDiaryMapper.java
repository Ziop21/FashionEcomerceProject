package project.fashionecommerce.backend.fashionecommerceproject.dto.stock.diary;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.stock.diary.StockDiaryEntity;
import java.util.List;

@Mapper(componentModel = "spring")
public interface StockDiaryMapper {
    StockDiaryEntity toEntity(StockDiary stockDiary);
    List<StockDiary> toDto(List<StockDiaryEntity> stockDiaryEntities );
    StockDiary toDto(StockDiaryEntity stockDiaryEntity);
    @Mapping(source = "id", target = "id", ignore = true)
    @Mapping(source = "createdAt", target = "createdAt", ignore = true)
    @Mapping(source = "createdBy", target = "createdBy", ignore = true)
    void updateExisted(StockDiary stockDiary, @MappingTarget StockDiaryEntity foundEntity);
}

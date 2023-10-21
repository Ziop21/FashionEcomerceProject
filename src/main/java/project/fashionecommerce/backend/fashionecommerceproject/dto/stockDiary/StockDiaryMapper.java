package project.fashionecommerce.backend.fashionecommerceproject.dto.stockDiary;

import org.mapstruct.Mapper;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.stockDiary.StockDiaryEntity;
import java.util.List;

@Mapper(componentModel = "spring")
public interface StockDiaryMapper {
    StockDiaryEntity toEntity(StockDiary stockDiary);
    List<StockDiary> toDto(List<StockDiaryEntity> stockDiaryEntities );
    StockDiary toDto(StockDiaryEntity stockDiaryEntity);

}

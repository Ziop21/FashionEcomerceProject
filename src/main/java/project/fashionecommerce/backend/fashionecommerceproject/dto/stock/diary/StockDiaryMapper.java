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

    @Mapping(source = "isActive", target = "isActive")
    StockDiary updateDtoIsActive(StockDiary stockDiary, Boolean isActive);

    @Mapping(source = "isDeleted", target = "isDeleted")
    StockDiary updateDtoIsDeleted(StockDiary stockDiary, Boolean isDeleted);

    @Mapping(source = "stockId", target = "stockId")
    @Mapping(source = "quantity", target = "quantity")
    @Mapping(source = "errorQuantity", target = "errorQuantity")
    @Mapping(source = "note", target = "note")
    StockDiary updateDto(StockDiary foundStockDiary, String stockId, Long quantity, Long errorQuantity, String note);
}

package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.stockDiary.models;

import org.mapstruct.Mapper;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stockDiary.StockDiary;
@Mapper(componentModel = "spring")
public interface StockDiaryModelMapper {

    StockDiary toDto(StockDiaryRequest stockDiaryRequest);
    StockDiaryResponse toModel(StockDiary stockDiary);
}

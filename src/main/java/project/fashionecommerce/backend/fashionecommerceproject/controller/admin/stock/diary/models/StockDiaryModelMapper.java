package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.stock.diary.models;

import org.mapstruct.Mapper;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.diary.StockDiary;

@Mapper(componentModel = "spring")
public interface StockDiaryModelMapper {
    StockDiary toDto(StockDiaryRequest stockDiaryRequest);
    StockDiaryResponse toModel(StockDiary stockDiary);
}

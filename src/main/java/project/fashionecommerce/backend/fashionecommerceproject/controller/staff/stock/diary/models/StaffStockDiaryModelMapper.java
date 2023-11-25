package project.fashionecommerce.backend.fashionecommerceproject.controller.staff.stock.diary.models;

import org.mapstruct.Mapper;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.diary.StockDiary;

@Mapper(componentModel = "spring")
public interface StaffStockDiaryModelMapper {
    StaffStockDiaryResponse toModel(StockDiary stockDiary);

    StockDiary toDto(StaffStockDiaryRequest staffStockDiaryRequest);
}

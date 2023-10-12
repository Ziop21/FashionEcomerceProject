package project.fashionecomerce.backend.fashionecomerceproject.dto.stockDiary;

import org.mapstruct.Mapper;
import project.fashionecomerce.backend.fashionecomerceproject.repository.stockDiary.StockDiaryEntity;

@Mapper(componentModel = "spring")
public interface StockDiaryMapper
{

    StockDiaryEntity toEntity(StockDiary stockDiary);

}

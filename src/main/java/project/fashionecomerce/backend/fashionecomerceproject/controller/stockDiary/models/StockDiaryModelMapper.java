package project.fashionecomerce.backend.fashionecomerceproject.controller.stockDiary.models;

import org.mapstruct.Mapper;

import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import project.fashionecomerce.backend.fashionecomerceproject.dto.stockDiary.StockDiary;

@Mapper
public interface StockDiaryModelMapper {
   StockDiaryModelMapper INSTANCE = Mappers.getMapper(StockDiaryModelMapper.class);

   @Mapping(source = "someField", target = "correspondingFieldInDTO")
   StockDiary toDTO(StockDiaryRequest stockDiaryRequest);
}

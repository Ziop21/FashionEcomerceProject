package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.stock.models;

import org.mapstruct.Mapper;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.Stock;

@Mapper(componentModel = "spring")
public interface StockModelMapper {
    Stock toDto(StockRequest stockRequest);

    StockResponse toModel(Stock stock);
}

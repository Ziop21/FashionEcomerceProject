package project.fashionecommerce.backend.fashionecommerceproject.controller.staff.stock.models;

import org.mapstruct.Mapper;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.Stock;

@Mapper(componentModel = "spring")
public interface StaffStockModelMapper {
    StaffStockResponse toModel(Stock stock);

    Stock toDto(StaffStockRequest staffStockRequest);
}

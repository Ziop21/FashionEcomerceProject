package project.fashionecommerce.backend.fashionecommerceproject.controller.guest.stock.models;

import org.mapstruct.Mapper;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.Stock;

@Mapper(componentModel = "spring")
public interface GuestStockModelMapper {
    GuestStockResponse toModel(Stock stock);
}

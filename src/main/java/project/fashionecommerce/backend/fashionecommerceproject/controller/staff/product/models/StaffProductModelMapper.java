package project.fashionecommerce.backend.fashionecommerceproject.controller.staff.product.models;

import org.mapstruct.Mapper;
import project.fashionecommerce.backend.fashionecommerceproject.dto.product.Product;

@Mapper(componentModel = "spring")
public interface StaffProductModelMapper {
    Product toDto(StaffProductRequest staffProductRequest);
}

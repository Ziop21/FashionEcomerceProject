package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.product.models;

import org.mapstruct.Mapper;
import project.fashionecommerce.backend.fashionecommerceproject.dto.product.Product;

@Mapper(componentModel = "spring")
public interface ProductModelMapper {
    Product toDto(ProductRequest productRequest);

    ProductResponse toModel(Product product);
}

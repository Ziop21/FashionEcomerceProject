package project.fashionecommerce.backend.fashionecommerceproject.dto.product;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import project.fashionecommerce.backend.fashionecommerceproject.dto.color.Color;
import project.fashionecommerce.backend.fashionecommerceproject.dto.size.Size;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.Stock;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.product.ProductEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductEntity toEntity(Product product);
    @Mapping(source = "id", target = "id", ignore = true)
    @Mapping(source = "createdBy", target = "createdBy", ignore = true)
    @Mapping(source = "createdAt", target = "createdAt", ignore = true)
    void updateExisted(Product product, @MappingTarget ProductEntity foundEntity);

    Product toDto(ProductEntity product);

    @Mapping(source = "stocks", target = "stocks")
    @Mapping(source = "sizes", target = "sizes")
    @Mapping(source = "colors", target = "colors")
    Product updateDto(Product product, List<Stock> stocks, List<Size> sizes, List<Color> colors);
}

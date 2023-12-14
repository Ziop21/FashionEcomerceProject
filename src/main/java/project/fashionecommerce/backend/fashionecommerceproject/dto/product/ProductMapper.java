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
    Product updateDto(Product product, List<Stock> stocks);

    @Mapping(source = "isActive", target = "isActive")
    Product updateDtoIsActive(Product product, Boolean isActive);
    @Mapping(source = "isSelling", target = "isSelling")
    Product updateDtoIsSelling(Product product, Boolean isSelling);
    @Mapping(source = "isDeleted", target = "isDeleted")
    Product updateDtoIsDeleted(Product product, Boolean isDeleted);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "promotionalPrice", target = "promotionalPrice")
    @Mapping(source = "images", target = "images")
    Product updateDto(Product foundProduct, String name, String description,
                      Double price, Double promotionalPrice, List<String> images);
    @Mapping(source = "view", target = "view")
    Product updateDtoView(Product product, Long view);
}

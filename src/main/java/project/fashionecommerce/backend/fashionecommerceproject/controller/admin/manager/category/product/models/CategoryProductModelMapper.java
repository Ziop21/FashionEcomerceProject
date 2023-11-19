package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.category.product.models;

import org.mapstruct.Mapper;
import project.fashionecommerce.backend.fashionecommerceproject.dto.category.product.CategoryProduct;

@Mapper(componentModel = "spring")
public interface CategoryProductModelMapper {
    CategoryProduct toDto(CategoryProductRequest categoryProductRequest);

    CategoryProductResponse toModel(CategoryProduct categoryProduct);
}

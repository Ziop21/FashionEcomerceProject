package project.fashionecommerce.backend.fashionecommerceproject.dto.category.product;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.category.product.CategoryProductEntity;

@Mapper(componentModel = "spring")
public interface CategoryProductMapper {
    CategoryProductEntity toEntity(CategoryProduct categoryProduct);
    @Mapping(source = "id", target = "id", ignore = true)
    @Mapping(source = "createdAt", target = "createdAt", ignore = true)
    @Mapping(source = "createdBy", target = "createdBy", ignore = true)
    void updateExisted(CategoryProduct categoryProduct, @MappingTarget CategoryProductEntity foundEntity);

    CategoryProduct toDto(CategoryProductEntity categoryProductEntity);

//    public CategoryProductEntity toEntity(CategoryProduct categoryProduct){
//        Optional<List<String>> categoryIds = Optional.ofNullable(categoryProduct.categoryIds());
//        List<ObjectId> objectIds = new ArrayList<>();
//
//        if (!categoryIds.isEmpty()){
//            objectIds = categoryIds.get().stream()
//                    .map(categoryId -> new ObjectId(categoryId))
//                    .collect(Collectors.toList());
//        }
//        CategoryProductEntity categoryProductEntity = new CategoryProductEntity();
//        categoryProductEntity.setCategoryIds(objectIds);
//        categoryProductEntity.setProductId(categoryProduct.productId());
//        categoryProductEntity.setIsDeleted(categoryProduct.isDeleted());
//        return categoryProductEntity;
//    }
//    public CategoryProduct toDto(CategoryProductEntity categoryProductEntity){
//        Optional<List<ObjectId>> objectIds = Optional.ofNullable(categoryProductEntity.getCategoryIds());
//        List<String> stringIds = new ArrayList<>();
//
//        if (!objectIds.isEmpty()){
//            stringIds = objectIds.get().stream()
//                    .map(objectId -> objectId.toHexString())
//                    .collect(Collectors.toList());
//        }
//        CategoryProduct categoryProduct = CategoryProduct.builder()
//                .id(categoryProductEntity.getId())
//                .categoryIds(stringIds)
//                .productId(categoryProductEntity.getProductId())
//                .isDeleted(categoryProductEntity.getIsDeleted())
//                .createdAt(categoryProductEntity.getCreatedAt())
//                .createdBy(categoryProductEntity.getCreatedBy())
//                .updatedAt(categoryProductEntity.getUpdatedAt())
//                .updatedBy(categoryProductEntity.getUpdatedBy())
//                .build();
//        return categoryProduct;
//    }
//    public void updateExisted(CategoryProduct categoryProduct, @MappingTarget CategoryProductEntity categoryProductEntity){
//        Optional<List<String>> categoryIds = Optional.ofNullable(categoryProduct.categoryIds());
//        List<ObjectId> objectIds = new ArrayList<>();
//
//        if (!categoryIds.isEmpty()){
//            objectIds = categoryIds.get().stream()
//                    .map(categoryId -> new ObjectId(categoryId))
//                    .collect(Collectors.toList());
//        }
//        categoryProductEntity.setCategoryIds(objectIds);
//        categoryProductEntity.setProductId(categoryProduct.productId());
//        categoryProductEntity.setIsDeleted(categoryProduct.isDeleted());
//    }
}

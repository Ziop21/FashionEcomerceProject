package project.fashionecommerce.backend.fashionecommerceproject.dto.category;

import org.bson.types.ObjectId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.category.CategoryEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class CategoryMapper {
    public CategoryEntity toEntity(Category category){
        Optional<List<String>> categoryIds = Optional.ofNullable(category.categoryIds());
        List<ObjectId> objectIds = new ArrayList<>();

        if (!categoryIds.isEmpty()){
            objectIds = categoryIds.get().stream()
                    .map(categoryId -> new ObjectId(categoryId))
                    .collect(Collectors.toList());
        }
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setCategoryIds(objectIds);
        categoryEntity.setName(category.name());
        categoryEntity.setSlug(category.slug());
        categoryEntity.setImages(category.images());
        categoryEntity.setIsActive(category.isActive());
        categoryEntity.setIsDeleted(category.isDeleted());

        return categoryEntity;
    }
    public Category toDto(CategoryEntity categoryEntity){
        Optional<List<ObjectId>> objectIds = Optional.ofNullable(categoryEntity.getCategoryIds());
        List<String> stringIds = new ArrayList<>();

        if (!objectIds.isEmpty()){
            stringIds = objectIds.get().stream()
                    .map(objectId -> objectId.toHexString())
                    .collect(Collectors.toList());
        }
        Category category = Category.builder()
                .id(categoryEntity.getId())
                .categoryIds(stringIds)
                .name(categoryEntity.getName())
                .slug(categoryEntity.getSlug())
                .images(categoryEntity.getImages())
                .isActive(categoryEntity.getIsActive())
                .isDeleted(categoryEntity.getIsDeleted())
                .createdAt(categoryEntity.getCreatedAt())
                .createdBy(categoryEntity.getCreatedBy())
                .updatedAt(categoryEntity.getUpdatedAt())
                .updatedBy(categoryEntity.getUpdatedBy())
                .build();

        return category;
    }
    public void updateExisted(Category category, @MappingTarget CategoryEntity categoryEntity){
        Optional<List<String>> categoryIds = Optional.ofNullable(category.categoryIds());
        List<ObjectId> objectIds = new ArrayList<>();

        if (!categoryIds.isEmpty()){
            objectIds = categoryIds.get().stream()
                    .map(categoryId -> new ObjectId(categoryId))
                    .collect(Collectors.toList());
        }
        categoryEntity.setCategoryIds(objectIds);
        categoryEntity.setName(category.name());
        categoryEntity.setSlug(category.slug());
        categoryEntity.setImages(category.images());
        categoryEntity.setIsActive(category.isActive());
        categoryEntity.setIsDeleted(category.isDeleted());
    }

}

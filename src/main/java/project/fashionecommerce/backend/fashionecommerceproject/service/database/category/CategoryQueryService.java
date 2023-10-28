package project.fashionecommerce.backend.fashionecommerceproject.service.database.category;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import project.fashionecommerce.backend.fashionecommerceproject.dto.category.Category;
import project.fashionecommerce.backend.fashionecommerceproject.dto.category.CategoryId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.category.CategoryMapper;
import project.fashionecommerce.backend.fashionecommerceproject.dto.category.CategoryQuery;
import project.fashionecommerce.backend.fashionecommerceproject.exception.MyResourceNotFoundException;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.category.CategoryEntity;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.category.CategoryRepository;

@Service
@RequiredArgsConstructor
public class CategoryQueryService {
    @NonNull final CategoryRepository categoryRepository;
    @NonNull final CategoryMapper categoryMapper;
    public Page<Category> findAll(CategoryQuery categoryQuery, PageRequest pageRequest) {
        Page<CategoryEntity> categoryEntityPage = categoryRepository.customFindAll(categoryQuery.search(), pageRequest);
        return categoryEntityPage.map(categoryMapper::toDto);
    }

    public Category findById(CategoryId categoryId) {
        CategoryEntity categoryEntity = categoryRepository.findById(categoryId.id())
                .orElseThrow(MyResourceNotFoundException::new);
        return categoryMapper.toDto(categoryEntity);
    }
}

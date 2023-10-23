package project.fashionecommerce.backend.fashionecommerceproject.service.database.category;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import project.fashionecommerce.backend.fashionecommerceproject.dto.category.Category;
import project.fashionecommerce.backend.fashionecommerceproject.dto.category.CategoryId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.category.CategoryMapper;
import project.fashionecommerce.backend.fashionecommerceproject.exception.MyConflictsException;
import project.fashionecommerce.backend.fashionecommerceproject.exception.MyResourceNotFoundException;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.category.CategoryEntity;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.category.CategoryRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryCommandService {
    @NonNull final CategoryRepository categoryRepository;
    @NonNull final CategoryMapper categoryMapper;
    public void save(Category category) {
        if (categoryRepository.existsByName(category.name()))
            throw new MyConflictsException();
        CategoryEntity categoryEntity = categoryMapper.toEntity(category);
        categoryRepository.save(categoryEntity);
    }

    public void delete(CategoryId categoryId) {
        categoryRepository.deleteById(categoryId.id());
    }

    public void update(CategoryId categoryId, Category category) {
        CategoryEntity foundEntity = categoryRepository.findById(categoryId.id())
                .orElseThrow(MyResourceNotFoundException::new);
        if (!foundEntity.getName().equals(category.name())
        && categoryRepository.existsByName(category.name()))
            throw new MyConflictsException();
        categoryMapper.updateExisted(category, foundEntity);
        categoryRepository.save(foundEntity);
    }
}

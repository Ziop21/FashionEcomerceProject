package project.fashionecomerce.backend.fashionecomerceproject.service.User.Category;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.fashionecomerce.backend.fashionecomerceproject.controller.User.Category.models.CategoryRequest;
import project.fashionecomerce.backend.fashionecomerceproject.dto.User.Category.Category;
import project.fashionecomerce.backend.fashionecomerceproject.repository.User.Category.CategoryEntity;
import project.fashionecomerce.backend.fashionecomerceproject.repository.User.Category.CategoryRepository;


@RequiredArgsConstructor
@Service
public class CategoryCommandService {
    @NonNull
    final CategoryRepository categoryRepository;
    public void save(Category categoryRequest){
        CategoryEntity entity = mapper.toEntity(categoryRequest);
        categoryRepository.save(entity);
    }
}

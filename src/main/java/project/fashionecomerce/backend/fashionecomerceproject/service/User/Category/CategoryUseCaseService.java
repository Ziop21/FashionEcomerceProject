package project.fashionecomerce.backend.fashionecomerceproject.service.User.Category;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.fashionecomerce.backend.fashionecomerceproject.controller.User.Category.models.CategoryRequest;
import project.fashionecomerce.backend.fashionecomerceproject.dto.User.Category.Category;

@RequiredArgsConstructor
@Service
public class CategoryUseCaseService {
    @NonNull
    final CategoryCommandService categoryCommandService;
    @Transactional
    public void save(Category dto) {
        categoryCommandService.save(dto);
    }
}

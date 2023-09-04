package project.fashionecomerce.backend.fashionecomerceproject.controller.User.Category;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import project.fashionecomerce.backend.fashionecomerceproject.controller.User.Category.models.CategoryRequest;
import project.fashionecomerce.backend.fashionecomerceproject.controller.User.Category.models.CategoryResponse;


@RequestMapping("/user/category/{categoryId}")
public interface CategoryAPI {
    @GetMapping
    CategoryResponse findById(@PathVariable Long categoryId);

    @PutMapping
    void update(@PathVariable Long categoryId, @RequestBody CategoryRequest categoryRequest);

    @DeleteMapping
    void delete(@PathVariable Long categoryId);
}

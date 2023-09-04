package project.fashionecomerce.backend.fashionecomerceproject.controller.User.Category;

import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.fashionecomerce.backend.fashionecomerceproject.controller.User.Category.models.CategoryRequest;
import project.fashionecomerce.backend.fashionecomerceproject.controller.User.Category.models.CategoryResponse;

@RequestMapping("/user/category")
public interface CategoriesAPI {
    @PostMapping
    void create(CategoryRequest categoryRequest);
    @GetMapping
    PageImpl<CategoryResponse> findAll();
}

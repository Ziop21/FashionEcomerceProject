package project.fashionecommerce.backend.fashionecommerceproject.service.database.category.product;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.fashionecommerce.backend.fashionecommerceproject.dto.category.product.CategoryProduct;
import project.fashionecommerce.backend.fashionecommerceproject.dto.category.product.CategoryProductMapper;
import project.fashionecommerce.backend.fashionecommerceproject.dto.category.product.CategoryProductId;
import project.fashionecommerce.backend.fashionecommerceproject.exception.MyConflictsException;
import project.fashionecommerce.backend.fashionecommerceproject.exception.MyResourceNotFoundException;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.category.product.CategoryProductRepository;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.category.product.CategoryProductEntity;

@Service
@RequiredArgsConstructor
public class CategoryProductCommandService {
    @NonNull
    final CategoryProductRepository categoryProductRepository;
    @NonNull
    final CategoryProductMapper categoryProductMapper;

    public void save(CategoryProduct categoryProduct) {
        if (categoryProductRepository.existsByProductIdAndAndCategoryId(categoryProduct.productId()
                , categoryProduct.categoryId()))
            throw new MyConflictsException();
        CategoryProductEntity categoryProductEntity = categoryProductMapper.toEntity(categoryProduct);
        categoryProductRepository.save(categoryProductEntity);
    }

    public void update(CategoryProductId categoryProductId, CategoryProduct categoryProduct) {
        CategoryProductEntity foundEntity = categoryProductRepository.findById(categoryProductId.id()).orElseThrow(MyResourceNotFoundException::new);
        if (categoryProduct.categoryId().equals(foundEntity.getProductId())
                && categoryProduct.productId().equals(foundEntity.getProductId())
                && categoryProductRepository.existsByProductIdAndAndCategoryId(categoryProduct.productId()
                , categoryProduct.categoryId()))
            throw new MyConflictsException();
        categoryProductMapper.updateExisted(categoryProduct, foundEntity);
        categoryProductRepository.save(foundEntity);
    }

    public void delete(CategoryProductId categoryProductId) {
        categoryProductRepository.deleteById(categoryProductId.id());
    }
}

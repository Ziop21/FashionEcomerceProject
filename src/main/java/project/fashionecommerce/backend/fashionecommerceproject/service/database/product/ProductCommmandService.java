package project.fashionecommerce.backend.fashionecommerceproject.service.database.product;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.fashionecommerce.backend.fashionecommerceproject.dto.product.Product;
import project.fashionecommerce.backend.fashionecommerceproject.dto.product.ProductId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.product.ProductMapper;
import project.fashionecommerce.backend.fashionecommerceproject.exception.MyConflictsException;
import project.fashionecommerce.backend.fashionecommerceproject.exception.MyResourceNotFoundException;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.product.ProductEntity;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.product.ProductRepository;

@Service
@RequiredArgsConstructor
public class ProductCommmandService {
    @NonNull
    final ProductRepository productRepository;
    @NonNull
    final ProductMapper productMapper;
    public void save(Product product) {
        if (productRepository.existsByName(product.name()))
            throw new MyConflictsException();
        ProductEntity productEntity = productMapper.toEntity(product);
        if (productEntity.getView() == null)
            productEntity.setView(0l);
        if (productEntity.getRating() == null)
            productEntity.setRating(0.0);
        productRepository.save(productEntity);
    }

    public void update(ProductId productId, Product product) {
        ProductEntity foundEntity = productRepository.findById(productId.id()).orElseThrow(MyResourceNotFoundException::new);
        if (!product.name().equals(foundEntity.getName())
                && productRepository.existsByName(product.name()))
            throw new MyConflictsException();
        productMapper.updateExisted(product, foundEntity);
        productRepository.save(foundEntity);
    }

    public void delete(ProductId productId) {
        productRepository.deleteById(productId.id());
    }
}

package project.fashionecommerce.backend.fashionecommerceproject.service.database.product;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import project.fashionecommerce.backend.fashionecommerceproject.dto.product.Product;
import project.fashionecommerce.backend.fashionecommerceproject.dto.product.ProductId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.product.ProductQuery;

@Service
@RequiredArgsConstructor
public class ProductUseCaseService {
    @NonNull final ProductCommmandService productCommmandService;
    @NonNull final ProductQueryService productQueryService;
    public void update(ProductId productId, Product product) {
        productCommmandService.update(productId, product);
    }

    public void delete(ProductId productId) {
        productCommmandService.delete(productId);
    }

    public void save(Product product) {
        productCommmandService.save(product);
    }

    public Product findById(ProductId productId) {
        return productQueryService.findById(productId);
    }

    public Page<Product> findAll(ProductQuery productQuery, PageRequest pageRequest) {
        return productQueryService.findAll(productQuery, pageRequest);
    }
}

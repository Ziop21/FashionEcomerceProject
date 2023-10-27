package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.product;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.product.models.ProductModelMapper;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.product.models.ProductRequest;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.product.models.ProductResponse;
import project.fashionecommerce.backend.fashionecommerceproject.dto.product.Product;
import project.fashionecommerce.backend.fashionecommerceproject.dto.product.ProductId;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.product.ProductUseCaseService;

@RestController
@RequiredArgsConstructor
public class ProductController implements ProductAPI{
    @NonNull
    final ProductUseCaseService productUseCaseService;
    @NonNull
    final ProductModelMapper productModelMapper;
    @Override
    public void update(String productId, ProductRequest productRequest) {
        Product product = productModelMapper.toDto(productRequest);
        productUseCaseService.update(new ProductId(productId), product);
    }

    @Override
    public ResponseEntity<ProductResponse> findById(String productId) {
        Product product = productUseCaseService.findById(new ProductId(productId));
        ProductResponse productResponse = productModelMapper.toModel(product);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @Override
    public void delete(String productId) {
        productUseCaseService.delete(new ProductId(productId));
    }
    
}

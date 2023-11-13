package project.fashionecommerce.backend.fashionecommerceproject.service.staff;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.fashionecommerce.backend.fashionecommerceproject.config.security.userDetails.Implement.UserDetailsImpl;
import project.fashionecommerce.backend.fashionecommerceproject.dto.enums.ERole;
import project.fashionecommerce.backend.fashionecommerceproject.dto.product.Product;
import project.fashionecommerce.backend.fashionecommerceproject.dto.product.ProductId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.product.ProductMapper;
import project.fashionecommerce.backend.fashionecommerceproject.dto.product.ProductQuery;
import project.fashionecommerce.backend.fashionecommerceproject.exception.MyForbiddenException;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.product.ProductCommmandService;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.product.ProductQueryService;

@Service
@RequiredArgsConstructor
public class StaffProductUseCaseService {
    @NonNull final ProductCommmandService productCommmandService;
    @NonNull final ProductQueryService productQueryService;
    @NonNull final ProductMapper productMapper;

    @Transactional
    public void update(ProductId productId, Product newProduct) {
        Product foundProduct = productQueryService.findById(productId);
        if (foundProduct.isDeleted() || foundProduct.isActive() || foundProduct.isSelling()) {
            throw new MyForbiddenException();
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        if (!userDetails.getId().equals(foundProduct.createdBy())){
            throw new MyForbiddenException();
        }
        foundProduct = productMapper.updateDto(foundProduct, newProduct.name(), newProduct.description(),
                newProduct.price(), newProduct.promotionalPrice(), newProduct.images());
        productCommmandService.update(productId, foundProduct);
    }
    @Transactional
    public void delete(ProductId productId) {
        Product product = productQueryService.findById(productId);
        if (product.isDeleted() || product.isActive() || product.isSelling()) {
            throw new MyForbiddenException();
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        if (!userDetails.getId().equals(product.createdBy())){
            throw new MyForbiddenException();
        }
        product = productMapper.updateDtoIsDeleted(product, true);
        productCommmandService.update(productId, product);
    }
    @Transactional
    public void save(Product product) {
        product = productMapper.updateDtoIsActive(product, false);
        product = productMapper.updateDtoIsDeleted(product, false);
        product = productMapper.updateDtoIsSelling(product, false);
        productCommmandService.save(product);
    }

    public Page<Product> findAll(ProductQuery productQuery, PageRequest pageRequest) {
        Page<Product> productPage = productQueryService.findAll(productQuery, pageRequest, ERole.STAFF);
        return productPage;
    }

    public Product findById(ProductId productId) {
        Product product = productQueryService.findById(productId);
        if (product.isDeleted() || product.isActive() || product.isSelling()) {
            throw new MyForbiddenException();
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        if (!userDetails.getId().equals(product.createdBy())){
            throw new MyForbiddenException();
        }
        return product;
    }
}

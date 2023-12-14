package project.fashionecommerce.backend.fashionecommerceproject.service.guest;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.fashionecommerce.backend.fashionecommerceproject.dto.color.Color;
import project.fashionecommerce.backend.fashionecommerceproject.dto.color.ColorId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.enums.ERole;
import project.fashionecommerce.backend.fashionecommerceproject.dto.product.Product;
import project.fashionecommerce.backend.fashionecommerceproject.dto.product.ProductId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.product.ProductMapper;
import project.fashionecommerce.backend.fashionecommerceproject.dto.product.ProductQuery;
import project.fashionecommerce.backend.fashionecommerceproject.dto.size.Size;
import project.fashionecommerce.backend.fashionecommerceproject.dto.size.SizeId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.Stock;
import project.fashionecommerce.backend.fashionecommerceproject.exception.MyForbiddenException;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.color.ColorQueryService;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.product.ProductCommmandService;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.product.ProductQueryService;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.size.SizeQueryService;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.stock.StockQueryService;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.user.UserQueryService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GuestProductUseCaseService {
    @NonNull final ProductCommmandService productCommmandService;
    @NonNull final ProductQueryService productQueryService;
    @NonNull final StockQueryService stockQueryService;
    @NonNull final ColorQueryService colorQueryService;
    @NonNull final SizeQueryService sizeQueryService;
    @NonNull final UserQueryService userQueryService;

    @NonNull final ProductMapper productMapper;

    @Transactional
    public Product findById(ProductId productId) {
        Product product = productQueryService.findById(productId);
        product = productMapper.updateDtoView(product, product.view() != null ? product.view() + 1 : 0 + 1);

        productCommmandService.update(productId, product);

        if (!product.isSelling() || !product.isActive() || product.isDeleted())
            throw new MyForbiddenException();
        List<Stock> stocks = stockQueryService.findAllByProductId(productId);

        product = productMapper.updateDto(product, stocks);

        return product;
    }

    public Page<Product> findAllProduct(ProductQuery productQuery, PageRequest pageRequest) {
        return productQueryService.findAll(productQuery, pageRequest, ERole.GUEST);
    }

    public List<ProductId> findAllProductIds(ProductQuery productQuery, PageRequest pageRequest) {
        return productQueryService.findAllProductIds(productQuery, pageRequest, ERole.GUEST);
    }
}

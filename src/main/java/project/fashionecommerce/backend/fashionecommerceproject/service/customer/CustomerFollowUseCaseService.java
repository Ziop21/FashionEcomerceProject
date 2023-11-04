package project.fashionecommerce.backend.fashionecommerceproject.service.customer;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.fashionecommerce.backend.fashionecommerceproject.config.security.userDetails.Implement.UserDetailsImpl;
import project.fashionecommerce.backend.fashionecommerceproject.dto.follow.Follow;
import project.fashionecommerce.backend.fashionecommerceproject.dto.follow.FollowId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.follow.FollowMapper;
import project.fashionecommerce.backend.fashionecommerceproject.dto.follow.FollowQuery;
import project.fashionecommerce.backend.fashionecommerceproject.dto.product.Product;
import project.fashionecommerce.backend.fashionecommerceproject.dto.product.ProductId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.user.UserId;
import project.fashionecommerce.backend.fashionecommerceproject.exception.MyConflictsException;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.follow.FollowCommandService;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.follow.FollowQueryService;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.product.ProductQueryService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerFollowUseCaseService {
    @NonNull final FollowCommandService followCommandService;
    @NonNull final FollowQueryService followQueryService;
    @NonNull final ProductQueryService productQueryService;

    @NonNull final FollowMapper followMapper;

    @Transactional
    public void delete(FollowId followId) {
        Follow foundDto = followQueryService.findById(followId);
        foundDto = followMapper.updateIsActiveAndIsDeleted(foundDto,false, true);
        followCommandService.save(foundDto);
    }
    @Transactional
    public void save(ProductId productId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String userId = userDetails.getId();
        if (followQueryService.existByUserIdAndProductIdAndIsActive(new UserId(userId), productId, true)){
            throw new MyConflictsException();
        }
        Follow follow = Follow.builder()
                .userId(userId)
                .productId(productId.id())
                .isActive(true)
                .isDeleted(false)
                .build();
        followCommandService.save(follow);
    }

    public List<Follow> findAll(FollowQuery followQuery) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String userId = userDetails.getId();
        List<Follow> follows = followQueryService.findAllByUserId(new UserId(userId), followQuery);
        List<ProductId> productIds = follows.stream().map(follow -> new ProductId(follow.productId()))
                .collect(Collectors.toList());
        List<Product> products = productQueryService.findAllByIds(productIds);
        List<Follow> updatedFollow = new ArrayList<>();
        for (int i = 0; i < follows.size(); i++){
            Product product = products.get(i);
            updatedFollow.add(followMapper.updateDto(follows.get(i), product.name(),
                    product.price(), product.promotionalPrice(), product.images() == null ? null : product.images().get(0)));
        }
        return updatedFollow;
    }
}

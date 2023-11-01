package project.fashionecommerce.backend.fashionecommerceproject.service.database.cart;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import project.fashionecommerce.backend.fashionecommerceproject.dto.cart.Cart;
import project.fashionecommerce.backend.fashionecommerceproject.dto.cart.CartId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.cart.CartMapper;
import project.fashionecommerce.backend.fashionecommerceproject.dto.cart.CartQuery;
import project.fashionecommerce.backend.fashionecommerceproject.exception.MyResourceNotFoundException;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.cart.CartEntity;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.cart.CartRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartQueryService {
    @NonNull final CartRepository cartRepository;
    @NonNull final CartMapper cartMapper;
    @NonNull final MongoTemplate mongoTemplate;

    public Page<Cart> findAll(CartQuery cartQuery, PageRequest pageRequest) {
        Criteria criteria = new Criteria();

        if (cartQuery.search() != null && !cartQuery.search().isBlank()) {
            criteria.orOperator(
                    Criteria.where("users.firstName").regex(".*" + cartQuery.search() + ".*", "i"),
                    Criteria.where("users.lastName").regex(".*" + cartQuery.search() + ".*", "i")
            );
        }

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.lookup("user", "userId", "_id", "users"),
                Aggregation.unwind("users", true),
                Aggregation.match(criteria),
                Aggregation.skip(pageRequest.getPageNumber() * pageRequest.getPageSize()),
                Aggregation.limit(pageRequest.getPageSize())
        );

        AggregationResults<CartEntity> results = mongoTemplate.aggregate(aggregation, "cart", CartEntity.class);

        List<CartEntity> cartEntities = results.getMappedResults();

        Long total = (long) cartEntities.size();

        List<CartEntity> pagedStockList = cartEntities.subList(0, Math.min(pageRequest.getPageSize(), cartEntities.size()));
        Page<CartEntity> stockPage = PageableExecutionUtils.getPage(pagedStockList, pageRequest, () -> total);

        return new PageImpl<>(stockPage.getContent().stream()
                .map(cartMapper::toDto)
                .collect(Collectors.toList()), pageRequest, total);
    }

    public Cart findById(CartId cartId) {
        CartEntity cartEntity = cartRepository.findById(cartId.id())
                .orElseThrow(MyResourceNotFoundException::new);
        return cartMapper.toDto(cartEntity);
    }
}

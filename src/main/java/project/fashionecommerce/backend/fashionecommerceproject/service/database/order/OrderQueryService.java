package project.fashionecommerce.backend.fashionecommerceproject.service.database.order;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import project.fashionecommerce.backend.fashionecommerceproject.config.security.userDetails.Implement.UserDetailsImpl;
import project.fashionecommerce.backend.fashionecommerceproject.dto.enums.EOrderStatus;
import project.fashionecommerce.backend.fashionecommerceproject.dto.enums.ERole;
import project.fashionecommerce.backend.fashionecommerceproject.dto.order.Order;
import project.fashionecommerce.backend.fashionecommerceproject.dto.order.OrderId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.order.OrderMapper;
import project.fashionecommerce.backend.fashionecommerceproject.dto.order.OrderQuery;
import project.fashionecommerce.backend.fashionecommerceproject.exception.MyResourceNotFoundException;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.order.OrderRepository;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.order.OrderEntity;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderQueryService {
    @NonNull final OrderRepository orderRepository;
    @NonNull final OrderMapper orderMapper;
    @NonNull final MongoTemplate mongoTemplate;
    public Page<Order> findAll(OrderQuery orderQuery, PageRequest pageRequest, ERole role) {
        Criteria criteria = new Criteria();

        if (orderQuery.search() != null && !orderQuery.search().isBlank()) {
            criteria.orOperator(
                    Criteria.where("user.firstName").regex(".*" + orderQuery.search() + ".*", "i"),
                    Criteria.where("user.lastName").regex(".*" + orderQuery.search() + ".*", "i"),
                    Criteria.where("user.email").regex(".*" + orderQuery.search() + ".*", "i"),
                    Criteria.where("phone").regex(".*" + orderQuery.search() + ".*", "i"),
                    Criteria.where("address").regex(".*" + orderQuery.search() + ".*", "i")
            );
        }

        if (orderQuery.isPaidBefore() != null){
            criteria.and("isPaidBefore").is(orderQuery.isPaidBefore());
        }

        Optional<List<EOrderStatus>> statuses = Optional.ofNullable(orderQuery.statuses());
        if (!statuses.isEmpty() && !statuses.get().isEmpty()) {
            criteria.and("status").in(statuses.get());
        }

        Optional<List<String>> deliveryIds = Optional.ofNullable(orderQuery.deliveryIds());
        if (!deliveryIds.isEmpty() && !deliveryIds.get().isEmpty()) {
            criteria.and("deliveryId").in(deliveryIds.get().stream().map(userLevelId -> new ObjectId(userLevelId)).collect(Collectors.toList()));
        }

        if (orderQuery.fromDate() != null && orderQuery.toDate() != null){
            LocalDateTime newFromDate = LocalDateTime.parse(orderQuery.fromDate() + "T00:00:00");
            LocalDateTime newToDate = LocalDateTime.parse(orderQuery.toDate() + "T23:59:59");
            criteria.and("createdAt").gte(newFromDate).lte(newToDate);
        }

        if (role.equals(ERole.CUSTOMER)){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            criteria.and("userId").is(new ObjectId(userDetails.getId()));
            criteria.and("isActive").is(true);
            criteria.and("isDeleted").is(false);
        }

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.lookup("user", "userId", "_id", "user"),
                Aggregation.unwind("user", true),
                Aggregation.match(criteria),
                Aggregation.skip(pageRequest.getPageNumber() * pageRequest.getPageSize()),
                Aggregation.limit(pageRequest.getPageSize())
        );
        AggregationResults<OrderEntity> results = mongoTemplate.aggregate(aggregation, "order", OrderEntity.class);

        List<OrderEntity> orderList = results.getMappedResults();

        Long total = (long) orderList.size();

        List<OrderEntity> pagedOrderList = orderList.subList(0, Math.min(pageRequest.getPageSize(), orderList.size()));
        Page<OrderEntity> orderPage = PageableExecutionUtils.getPage(pagedOrderList, pageRequest, () -> total);

        return new PageImpl<>(orderPage.getContent().stream().map(orderMapper::toDto).collect(Collectors.toList()), pageRequest, total);

    }

    public Order findById(OrderId orderId) {
        OrderEntity orderEntity = orderRepository.findById(orderId.id()).orElseThrow(MyResourceNotFoundException::new);
        return orderMapper.toDto(orderEntity);
    }
}

package project.fashionecommerce.backend.fashionecommerceproject.service.database.follow;

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
import org.springframework.stereotype.Service;
import project.fashionecommerce.backend.fashionecommerceproject.dto.follow.Follow;
import project.fashionecommerce.backend.fashionecommerceproject.dto.follow.FollowId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.follow.FollowMapper;
import project.fashionecommerce.backend.fashionecommerceproject.dto.follow.FollowQuery;
import project.fashionecommerce.backend.fashionecommerceproject.dto.product.ProductId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.user.UserId;
import project.fashionecommerce.backend.fashionecommerceproject.exception.MyResourceNotFoundException;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.follow.FollowEntity;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.follow.FollowRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FollowQueryService {
    @NonNull
    final FollowRepository followRepository;
    @NonNull
    final FollowMapper followMapper;
    @NonNull
    final MongoTemplate mongoTemplate;

    public Follow findById(FollowId followId) {
        FollowEntity followEntity = followRepository.findById(followId.id())
                .orElseThrow(MyResourceNotFoundException::new);
        return followMapper.toDto(followEntity);
    }

    public Page<Follow> findAll(FollowQuery followQuery, PageRequest pageRequest) {
        Criteria criteria = new Criteria();

        if (followQuery.search() != null && !followQuery.search().isBlank()) {
            criteria.orOperator(
                    Criteria.where("user.firstName").regex(".*" + followQuery.search() + ".*", "i"),
                    Criteria.where("user.lastName").regex(".*" + followQuery.search() + ".*", "i"),
                    Criteria.where("user.email").regex(".*" + followQuery.search() + ".*", "i"),
                    Criteria.where("product.name").regex(".*" + followQuery.search() + ".*", "i"),
                    Criteria.where("product.description").regex(".*" + followQuery.search() + ".*", "i")
            );
        }

        if (followQuery.fromDate() != null && followQuery.toDate() != null){
            LocalDateTime newFromDate = LocalDateTime.parse(followQuery.fromDate() + "T00:00:00");
            LocalDateTime newToDate = LocalDateTime.parse(followQuery.toDate() + "T23:59:59");
            criteria.and("createdAt").gte(newFromDate).lte(newToDate);
        }

        Aggregation countAggregation = Aggregation.newAggregation(
                Aggregation.lookup("user", "userId", "_id", "user"),
                Aggregation.unwind("user", true),
                Aggregation.lookup("product", "productId", "_id", "product"),
                Aggregation.unwind("product", true),
                Aggregation.match(criteria),
                Aggregation.group().count().as("totalRecords")
        );

        AggregationResults<Map> countResults = mongoTemplate.aggregate(countAggregation, "follow", Map.class);
        Long total = countResults.getMappedResults().size() == 0 ? 0 : Long.parseLong(countResults.getMappedResults().get(0).get("totalRecords").toString());

        int currentPage = pageRequest.getPageNumber();
        int totalPages = (int) Math.ceil((double) total / pageRequest.getPageSize());
        if (currentPage > totalPages) {
            currentPage = totalPages - 1;
        }

        Aggregation mainAggregation = Aggregation.newAggregation(
                Aggregation.lookup("user", "userId", "_id", "user"),
                Aggregation.unwind("user", true),
                Aggregation.lookup("product", "productId", "_id", "product"),
                Aggregation.unwind("product", true),
                Aggregation.match(criteria),
                Aggregation.skip(currentPage * pageRequest.getPageSize()),
                Aggregation.limit(pageRequest.getPageSize())
        );

        PageRequest newPageRequest = PageRequest.of(currentPage, pageRequest.getPageSize(), pageRequest.getSort());

        AggregationResults<FollowEntity> results = mongoTemplate.aggregate(mainAggregation, "follow", FollowEntity.class);
        List<FollowEntity> followList = results.getMappedResults();

        List<FollowEntity> pagedFollowList = followList.subList(0, Math.min(pageRequest.getPageSize(), followList.size()));
        Page<FollowEntity> followPage = PageableExecutionUtils.getPage(pagedFollowList, newPageRequest, () -> total);

        return new PageImpl<>(followPage.getContent().stream().map(followMapper::toDto).collect(Collectors.toList()), pageRequest, total);
    }

    public List<Follow> findAllByUserId(UserId userId, FollowQuery followQuery) {
        Criteria criteria = new Criteria();

        criteria.and("userId").is(new ObjectId(userId.id()));
        criteria.and("isActive").is(true);

        if (followQuery.search() != null && !followQuery.search().isBlank()) {
            criteria.orOperator(
                    Criteria.where("product.name").regex(".*" + followQuery.search() + ".*", "i"),
                    Criteria.where("product.description").regex(".*" + followQuery.search() + ".*", "i")
            );
        }

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.lookup("product", "productId", "_id", "product"),
                Aggregation.unwind("product", true),
                Aggregation.match(criteria)
        );

        AggregationResults<FollowEntity> results = mongoTemplate.aggregate(aggregation, "follow", FollowEntity.class);

        List<FollowEntity> followList = results.getMappedResults();

        return followList.stream().map(followMapper::toDto).collect(Collectors.toList());
    }

    public Boolean existByUserIdAndProductIdAndIsActive(UserId userId, ProductId productId, Boolean isActive) {
        if (followRepository.existsByUserIdAndProductIdAndIsActive(userId.id(), productId.id(), isActive))
            return true;
        return false;
    }
}

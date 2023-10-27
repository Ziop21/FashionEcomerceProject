package project.fashionecommerce.backend.fashionecommerceproject.service.database.user;

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
import project.fashionecommerce.backend.fashionecommerceproject.dto.user.User;
import project.fashionecommerce.backend.fashionecommerceproject.dto.user.UserId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.user.UserMapper;
import project.fashionecommerce.backend.fashionecommerceproject.dto.user.level.UserQuery;
import project.fashionecommerce.backend.fashionecommerceproject.exception.MyResourceNotFoundException;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.user.UserEntity;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.token.TokenRepository;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.user.UserRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserQueryService {
    @NonNull final UserRepository userRepository;
    @NonNull final UserMapper userMapper;
    @NonNull final MongoTemplate mongoTemplate;
    @NonNull final TokenRepository tokenRepository;

    public Boolean existsByEmail(String email){
        if (!userRepository.existsByEmailAndIsDeleted(email, false))
            return false;

        UserEntity userEntity = userRepository.findByEmailAndIsDeleted(email, false)
                .orElseThrow(MyResourceNotFoundException::new);
        if (!userEntity.getIsEmailActive()){
            userRepository.deleteById(userEntity.getId());
            tokenRepository.deleteByUserId(userEntity.getId());
            return false;
        }
        return true;
    }

    public User findByEmail(String email) {
        UserEntity user = userRepository.findByEmailAndIsDeletedAndIsEmailActive(email, false, true)
                .orElseThrow(MyResourceNotFoundException::new);
        return userMapper.toDto(user);
    }

    public User findById(UserId userId) {
        UserEntity user = userRepository.findById(userId.id()).orElseThrow(MyResourceNotFoundException::new);
        return userMapper.toDto(user);
    }

    public Page<User> findAll(UserQuery userQuery, PageRequest pageRequest) {
        Criteria criteria = new Criteria();

        if (userQuery.search() != null && !userQuery.search().isBlank()) {
            criteria.orOperator(
                    Criteria.where("firstName").regex(".*" + userQuery.search() + ".*", "i"),
                    Criteria.where("lastName").regex(".*" + userQuery.search() + ".*", "i"),
                    Criteria.where("slug").regex(".*" + userQuery.search() + ".*", "i"),
                    Criteria.where("idCard").regex(".*" + userQuery.search() + ".*", "i"),
                    Criteria.where("email").regex(".*" + userQuery.search() + ".*", "i"),
                    Criteria.where("phone").regex(".*" + userQuery.search() + ".*", "i"),
                    Criteria.where("eWallet").regex(".*" + userQuery.search() + ".*", "i")
            );
        }

        if (userQuery.isEmailActive() != null){
            criteria.and("isEmailActive").is(userQuery.isEmailActive());
        }

        if (userQuery.isPhoneActive() != null){
            criteria.and("isPhoneActive").is(userQuery.isPhoneActive());
        }

        if (userQuery.isDeleted() != null){
            criteria.and("isDeleted").is(userQuery.isDeleted());
        }

        Optional<List<String>> userLevelIds = Optional.ofNullable(userQuery.userLevelIds());
        if (!userLevelIds.isEmpty() && !userLevelIds.get().isEmpty()) {
            criteria.and("userLevelId").in(userLevelIds.get().stream().map(userLevelId -> new ObjectId(userLevelId)).collect(Collectors.toList()));
        }

        if (userQuery.fromDate() != null && userQuery.toDate() != null){
            LocalDateTime newFromDate = LocalDateTime.parse(userQuery.fromDate() + "T00:00:00");
            LocalDateTime newToDate = LocalDateTime.parse(userQuery.toDate() + "T23:59:59");
            criteria.and("createdAt").gte(newFromDate).lte(newToDate);
        }

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(criteria),
                Aggregation.skip(pageRequest.getPageNumber() * pageRequest.getPageSize()),
                Aggregation.limit(pageRequest.getPageSize())
        );

        AggregationResults<UserEntity> results = mongoTemplate.aggregate(aggregation, "user", UserEntity.class);

        List<UserEntity> userList = results.getMappedResults();

        Long total = (long) userList.size();

        List<UserEntity> pagedUserList = userList.subList(0, Math.min(pageRequest.getPageSize(), userList.size()));
        Page<UserEntity> userPage = PageableExecutionUtils.getPage(pagedUserList, pageRequest, () -> total);

        return new PageImpl<>(userPage.getContent().stream().map(userMapper::toDto).collect(Collectors.toList()), pageRequest, total);
    }
}

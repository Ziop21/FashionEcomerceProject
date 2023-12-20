package project.fashionecommerce.backend.fashionecommerceproject.service.database.stock.diary;

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
import project.fashionecommerce.backend.fashionecommerceproject.dto.enums.ERole;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.diary.StockDiary;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.diary.StockDiaryId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.diary.StockDiaryMapper;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.diary.StockDiaryQuery;
import project.fashionecommerce.backend.fashionecommerceproject.exception.MyResourceNotFoundException;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.stock.diary.StockDiaryEntity;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.stock.diary.StockDiaryRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StockDiaryQueryService {
    @NonNull
    final StockDiaryRepository stockDiaryRepository;
    @NonNull
    final StockDiaryMapper stockDiaryMapper;
    @NonNull
    final MongoTemplate mongoTemplate;
    public Page<StockDiary> findAll(StockDiaryQuery stockDiaryQuery, PageRequest pageRequest, ERole role) {
        Criteria criteria = new Criteria();

        if (stockDiaryQuery.search() != null && !stockDiaryQuery.search().isBlank()) {
            criteria.orOperator(
                    Criteria.where("users.firstName").regex(".*" + stockDiaryQuery.search() + ".*", "i"),
                    Criteria.where("users.lastName").regex(".*" + stockDiaryQuery.search() + ".*", "i")
            );
        }

        if (stockDiaryQuery.fromDate() != null && stockDiaryQuery.toDate() != null){
            LocalDateTime newFromDate = LocalDateTime.parse(stockDiaryQuery.fromDate() + "T00:00:00");
            LocalDateTime newToDate = LocalDateTime.parse(stockDiaryQuery.toDate() + "T23:59:59");
            criteria.and("createdAt").gte(newFromDate).lte(newToDate);
        }
        if (role.equals(ERole.STAFF)){
            criteria.and("isActive").is(false);
            criteria.and("isDeleted").is(false);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            criteria.and("createdBy").is(new ObjectId(userDetails.getId()));
        }

        Aggregation countAggregation = Aggregation.newAggregation(
                Aggregation.lookup("user", "createdBy", "_id", "users"),
                Aggregation.unwind("users", true),
                Aggregation.match(criteria),
                Aggregation.group().count().as("totalRecords")
        );

        AggregationResults<Map> countResults = mongoTemplate.aggregate(countAggregation, "stock_diary", Map.class);
        Long total = countResults.getMappedResults().size() == 0 ? 0 : Long.parseLong(countResults.getMappedResults().get(0).get("totalRecords").toString());

        int currentPage = pageRequest.getPageNumber();
        int totalPages = (int) Math.ceil((double) total / pageRequest.getPageSize());
        if (currentPage > totalPages) {
            currentPage = totalPages - 1;
        }

        Aggregation mainAggregation = Aggregation.newAggregation(
                Aggregation.lookup("user", "createdBy", "_id", "users"),
                Aggregation.unwind("users", true),
                Aggregation.match(criteria),
                Aggregation.skip(currentPage * pageRequest.getPageSize()),
                Aggregation.limit(pageRequest.getPageSize())
        );

        PageRequest newPageRequest = PageRequest.of(currentPage, pageRequest.getPageSize(), pageRequest.getSort());

        AggregationResults<StockDiaryEntity> results = mongoTemplate.aggregate(mainAggregation, "stock_diary", StockDiaryEntity.class);
        List<StockDiaryEntity> stockDiaryList = results.getMappedResults();

        List<StockDiaryEntity> pagedStockDiaryList = stockDiaryList.subList(0, Math.min(pageRequest.getPageSize(), stockDiaryList.size()));
        Page<StockDiaryEntity> stockDiaryPage = PageableExecutionUtils.getPage(pagedStockDiaryList, newPageRequest, () -> total);
        return new PageImpl<>(stockDiaryPage.getContent().stream().map(stockDiaryMapper::toDto).collect(Collectors.toList()), pageRequest, total);
    }
    public StockDiary findById(StockDiaryId stockDiaryId) {
        StockDiaryEntity stockDiaryEntity = stockDiaryRepository.findById(stockDiaryId.id()).orElseThrow(MyResourceNotFoundException::new);
        return stockDiaryMapper.toDto(stockDiaryEntity);
    }
}


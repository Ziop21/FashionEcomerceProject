package project.fashionecommerce.backend.fashionecommerceproject.dto.stock;

import org.bson.types.ObjectId;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import project.fashionecommerce.backend.fashionecommerceproject.dto.review.Review;
import project.fashionecommerce.backend.fashionecommerceproject.dto.review.ReviewMapper;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.review.ReviewEntity;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.stock.StockEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class StockMapper {
    @Autowired
    ReviewMapper reviewMapper;
    public StockEntity toEntity(Stock stock){
        Optional<List<String>> colorsIds = Optional.ofNullable(stock.colorIds());
        List<ObjectId> objectIds = new ArrayList<>();

        if (!colorsIds.isEmpty()){
            objectIds = colorsIds.get().stream()
                    .map(colorsId -> new ObjectId(colorsId))
                    .collect(Collectors.toList());
        }

        Optional<List<Review>> reviews = Optional.ofNullable(stock.reviews());
        List<ReviewEntity> reviewEntities = new ArrayList<>();
        if (!reviews.isEmpty()){
            reviewEntities = reviews.get().stream()
                    .map(reviewMapper::toEntity).collect(Collectors.toList());
        }

        StockEntity stockEntity = new StockEntity();
        stockEntity.setProductId(stock.productId());
        stockEntity.setSizeId(stock.sizeId());
        stockEntity.setColorIds(objectIds);
        stockEntity.setQuantity(stock.quantity());
        stockEntity.setIsMixedColor(stock.isMixedColor());
        stockEntity.setIsActive(stock.isActive());
        stockEntity.setIsDeleted(stock.isDeleted());
        stockEntity.setReviews(reviewEntities);
        return stockEntity;
    }

    public Stock toDto(StockEntity stockEntity){
        Optional<List<ObjectId>> colorsIds = Optional.ofNullable(stockEntity.getColorIds());
        List<String> stringIds = new ArrayList<>();

        if (!colorsIds.isEmpty()){
            stringIds = colorsIds.get().stream()
                    .map(colorsId -> colorsId.toHexString())
                    .collect(Collectors.toList());
        }

        Optional<List<ReviewEntity>> reviewEntities = Optional.ofNullable(stockEntity.getReviews());
        List<Review> reviews = new ArrayList<>();
        if (!reviewEntities.isEmpty()){
            reviews = reviewEntities.get().stream()
                    .map(reviewMapper::toDto).collect(Collectors.toList());
        }

        Stock stock = Stock.builder()
                .id(stockEntity.getId())
                .productId(stockEntity.getProductId())
                .sizeId(stockEntity.getSizeId())
                .colorIds(stringIds)
                .quantity(stockEntity.getQuantity())
                .isMixedColor(stockEntity.getIsMixedColor())
                .isDeleted(stockEntity.getIsDeleted())
                .isActive(stockEntity.getIsActive())
                .reviews(reviews)
                .updatedAt(stockEntity.getUpdatedAt())
                .updatedBy(stockEntity.getUpdatedBy())
                .createdBy(stockEntity.getCreatedBy())
                .createdAt(stockEntity.getCreatedAt())
                .build();
        return stock;
    }

    public void updateExisted(Stock stock, @MappingTarget StockEntity foundEntity){
        Optional<List<String>> colorsIds = Optional.ofNullable(stock.colorIds());
        List<ObjectId> objectIds = new ArrayList<>();

        if (!colorsIds.isEmpty()){
            objectIds = colorsIds.get().stream()
                    .map(colorsId -> new ObjectId(colorsId))
                    .collect(Collectors.toList());
        }

        Optional<List<Review>> reviews = Optional.ofNullable(stock.reviews());
        List<ReviewEntity> reviewEntities = new ArrayList<>();
        if (!reviews.isEmpty()){
            reviewEntities = reviews.get().stream()
                    .map(reviewMapper::toEntity).collect(Collectors.toList());
        }

        foundEntity.setProductId(stock.productId());
        foundEntity.setSizeId(stock.sizeId());
        foundEntity.setColorIds(objectIds);
        foundEntity.setQuantity(stock.quantity());
        foundEntity.setIsMixedColor(stock.isMixedColor());
        foundEntity.setIsActive(stock.isActive());
        foundEntity.setIsDeleted(stock.isDeleted());
        foundEntity.setReviews(reviewEntities);
    }
}

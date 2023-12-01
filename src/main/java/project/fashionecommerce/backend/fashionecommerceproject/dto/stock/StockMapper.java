package project.fashionecommerce.backend.fashionecommerceproject.dto.stock;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
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
        Optional<List<Review>> reviews = Optional.ofNullable(stock.reviews());
        List<ReviewEntity> reviewEntities = new ArrayList<>();
        if (!reviews.isEmpty()){
            reviewEntities = reviews.get().stream()
                    .map(reviewMapper::toEntity).collect(Collectors.toList());
        }

        StockEntity stockEntity = new StockEntity();
        stockEntity.setProductId(stock.productId());
        stockEntity.setSizeId(stock.sizeId());
        stockEntity.setColorId(stock.colorId());
        stockEntity.setQuantity(stock.quantity());
        stockEntity.setIsActive(stock.isActive());
        stockEntity.setIsDeleted(stock.isDeleted());
        stockEntity.setReviews(reviewEntities);
        return stockEntity;
    }

    public Stock toDto(StockEntity stockEntity){
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
                .colorId(stockEntity.getColorId())
                .quantity(stockEntity.getQuantity())
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
        Optional<List<Review>> reviews = Optional.ofNullable(stock.reviews());
        List<ReviewEntity> reviewEntities = new ArrayList<>();
        if (!reviews.isEmpty()){
            reviewEntities = reviews.get().stream()
                    .map(reviewMapper::toEntity).collect(Collectors.toList());
        }

        foundEntity.setProductId(stock.productId());
        foundEntity.setSizeId(stock.sizeId());
        foundEntity.setColorId(stock.colorId());
        foundEntity.setQuantity(stock.quantity());
        foundEntity.setIsActive(stock.isActive());
        foundEntity.setIsDeleted(stock.isDeleted());
        foundEntity.setReviews(reviewEntities);
    }
    @Mapping(source = "reviews", target = "reviews")
    public abstract Stock updateDto(Stock stock, List<Review> reviews);

    @Mapping(source = "isActive", target = "isActive")
    public abstract Stock updateDtoIsActive(Stock stock, Boolean isActive);

    @Mapping(source = "isDeleted", target = "isDeleted")
    public abstract Stock updateDtoIsDeleted(Stock stock, Boolean isDeleted);

    @Mapping(source = "productId", target = "productId")
    @Mapping(source = "colorId", target = "colorId")
    @Mapping(source = "sizeId", target = "sizeId")
    @Mapping(source = "quantity", target = "quantity")
    public abstract Stock updateDto(Stock foundStock, String productId, String colorId, String sizeId, Long quantity);

    @Mapping(source = "productName", target = "productName")
    public abstract Stock updateDtoProductName(Stock stock, String productName);

    @Mapping(source = "sizeName", target = "sizeName")
    public abstract Stock updateDtoSizeName(Stock stock, String sizeName);

    @Mapping(source = "colorName", target = "colorName")
    public abstract Stock updateDtoColorName(Stock stock, String colorName);
}

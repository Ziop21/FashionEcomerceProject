package project.fashionecommerce.backend.fashionecommerceproject.dto.stock.diary;

import lombok.Builder;

import java.time.LocalDateTime;
@Builder
public record StockDiaryQuery(
        String search ,
        LocalDateTime fromDate,
        LocalDateTime toDate
) {
}

package project.fashionecommerce.backend.fashionecommerceproject.dto.stock.diary;

import java.time.LocalDateTime;

public record StockDiaryQuery(
        LocalDateTime fromDate,
        LocalDateTime toDate
) {
}

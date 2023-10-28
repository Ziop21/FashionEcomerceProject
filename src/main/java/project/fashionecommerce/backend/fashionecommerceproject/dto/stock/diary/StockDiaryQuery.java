package project.fashionecommerce.backend.fashionecommerceproject.dto.stock.diary;

import lombok.Builder;
import java.time.LocalDate;

@Builder
public record StockDiaryQuery(
        String search ,
        LocalDate fromDate,
        LocalDate toDate
) {
}

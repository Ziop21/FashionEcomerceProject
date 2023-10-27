package project.fashionecommerce.backend.fashionecommerceproject.dto.product;

import lombok.Builder;
import java.time.LocalDate;

@Builder
public record ProductQuery(
        String search,
        LocalDate fromDate,
        LocalDate toDate
) {
}

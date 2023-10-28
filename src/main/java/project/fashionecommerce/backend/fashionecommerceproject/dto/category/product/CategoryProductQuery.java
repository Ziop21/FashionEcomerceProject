package project.fashionecommerce.backend.fashionecommerceproject.dto.category.product;

import lombok.Builder;
import java.time.LocalDate;

@Builder
public record CategoryProductQuery(
        String search,
        LocalDate fromDate,
        LocalDate toDate
) {
}

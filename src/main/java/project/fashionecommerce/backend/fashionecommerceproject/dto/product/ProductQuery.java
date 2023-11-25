package project.fashionecommerce.backend.fashionecommerceproject.dto.product;

import lombok.Builder;
import java.time.LocalDate;
import java.util.List;

@Builder
public record ProductQuery(
        String search,
        List<String> sizeIds,
        List<String> colorIds,
        Long fromRating,
        Long toRating,
        Long fromPrice,
        Long toPrice,
        LocalDate fromDate,
        LocalDate toDate
) {
}

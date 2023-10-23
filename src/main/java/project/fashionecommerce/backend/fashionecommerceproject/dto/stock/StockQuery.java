package project.fashionecommerce.backend.fashionecommerceproject.dto.stock;

import lombok.Builder;
import java.util.List;

@Builder
public record StockQuery(
        String searchString,
        List<String> colorIds,
        List<String> sizeIds
) {
}

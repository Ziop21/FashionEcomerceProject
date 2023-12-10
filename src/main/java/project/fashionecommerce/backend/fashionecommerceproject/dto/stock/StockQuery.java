package project.fashionecommerce.backend.fashionecommerceproject.dto.stock;

import lombok.Builder;
import java.util.List;

@Builder
public record StockQuery(
        String productId,
        String search,
        List<String> colorIds,
        List<String> sizeIds
) {
}

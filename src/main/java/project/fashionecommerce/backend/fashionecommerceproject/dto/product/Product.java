package project.fashionecommerce.backend.fashionecommerceproject.dto.product;

import project.fashionecommerce.backend.fashionecommerceproject.dto.color.Color;
import project.fashionecommerce.backend.fashionecommerceproject.dto.size.Size;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.Stock;
import java.time.LocalDateTime;
import java.util.List;

public record Product (
        String id,
        String name,
        String slug,
        String description,
        Double price,
        Double promotionalPrice,
        Long view,
        Boolean isSelling,
        List<String> images,
        Double rating,
        String createdBy,
        String updatedBy,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Boolean isDeleted,
        Boolean isActive,
        List<Color> colors,
        List<Size> sizes,
        List<Stock> stocks
) {
}

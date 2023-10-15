package project.fashionecommerce.backend.fashionecommerceproject.dto.color;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record Color(
        String id,
        String name,
        String updatedBy,
        String createdBy,
        LocalDateTime createdAt,
        LocalDate updatedAt
) {
}

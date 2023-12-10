package project.fashionecommerce.backend.fashionecommerceproject.dto.category;

import lombok.Builder;

@Builder
public record CategoryQuery(
        String search
) {
}

package project.fashionecommerce.backend.fashionecommerceproject.dto.follow;

import lombok.Builder;
import java.time.LocalDate;

@Builder
public record FollowQuery(
        String search,
        LocalDate fromDate,
        LocalDate toDate
) {
}

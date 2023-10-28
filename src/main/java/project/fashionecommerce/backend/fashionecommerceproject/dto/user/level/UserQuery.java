package project.fashionecommerce.backend.fashionecommerceproject.dto.user.level;

import lombok.Builder;
import java.time.LocalDate;
import java.util.List;
@Builder
public record UserQuery(
        String search,
        Boolean isDeleted,
        Boolean isEmailActive,
        Boolean isPhoneActive,
        List<String> userLevelIds,
        LocalDate fromDate,
        LocalDate toDate
) {
}

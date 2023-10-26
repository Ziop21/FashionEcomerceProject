package project.fashionecommerce.backend.fashionecommerceproject.dto.order;

import lombok.Builder;
import project.fashionecommerce.backend.fashionecommerceproject.dto.enums.EOrderStatus;
import java.time.LocalDate;
import java.util.List;
@Builder
public record OrderQuery(
        String search,
        List<String> deliveryIds,
        List<EOrderStatus> statuses,
        Boolean isPaidBefore,
        LocalDate fromDate,
        LocalDate toDate
) {
}

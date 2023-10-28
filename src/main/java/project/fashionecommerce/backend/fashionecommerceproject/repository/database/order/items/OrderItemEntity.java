package project.fashionecommerce.backend.fashionecommerceproject.repository.database.order.items;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import project.fashionecommerce.backend.fashionecommerceproject.repository.auditable.TimeAuditable;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class OrderItemEntity extends TimeAuditable implements Serializable {
    private Long quantity;
    private String stockId;
}

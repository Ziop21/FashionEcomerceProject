package project.fashionecommerce.backend.fashionecommerceproject.repository.database.cart.items;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import project.fashionecommerce.backend.fashionecommerceproject.repository.auditable.TimeAuditable;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Document(value = "cart_item")
public class CartItemEntity extends TimeAuditable implements Serializable {
    @Field(targetType = FieldType.OBJECT_ID)
    private String stockId;
    private Long quantity;
}

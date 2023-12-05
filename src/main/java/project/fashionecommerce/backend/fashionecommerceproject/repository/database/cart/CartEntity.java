package project.fashionecommerce.backend.fashionecommerceproject.repository.database.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import project.fashionecommerce.backend.fashionecommerceproject.repository.auditable.FullAuditable;
import project.fashionecommerce.backend.fashionecommerceproject.repository.auditable.TimeAuditable;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.cart.items.CartItemEntity;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Document(value = "cart")
public class CartEntity extends FullAuditable implements Serializable {
    @Id
    private String id;
    List<CartItemEntity> cartItems;
}

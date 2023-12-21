package project.fashionecommerce.backend.fashionecommerceproject.repository.database.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import project.fashionecommerce.backend.fashionecommerceproject.dto.enums.EOrderStatus;
import project.fashionecommerce.backend.fashionecommerceproject.repository.auditable.TimeAuditable;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.order.items.OrderItemEntity;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Document(value = "order")
public class OrderEntity extends TimeAuditable implements Serializable {
    @Id
    private String id;
    @Field(targetType = FieldType.OBJECT_ID)
    private String userId;
    private String username;
    private String address;
    private String phone;
    private List<OrderItemEntity> orderItems;
    @Field(targetType = FieldType.OBJECT_ID)
    private String deliveryId;
    private Double shippingFee;
    private EOrderStatus status;
    private Boolean isPaidBefore;
}

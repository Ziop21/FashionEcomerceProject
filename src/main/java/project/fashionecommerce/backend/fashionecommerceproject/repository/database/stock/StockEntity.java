package project.fashionecommerce.backend.fashionecommerceproject.repository.database.stock;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import project.fashionecommerce.backend.fashionecommerceproject.repository.auditable.FullAuditable;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.review.ReviewEntity;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Document(value = "stock")
public class StockEntity extends FullAuditable implements Serializable {
    @Id
    private String id;
    @Field(targetType = FieldType.OBJECT_ID)
    private String productId;
    @Field(targetType = FieldType.OBJECT_ID)
    private String sizeId;
    @Field(targetType = FieldType.OBJECT_ID)
    private String colorId;
    private Long quantity;
    private List<ReviewEntity> reviews;
}

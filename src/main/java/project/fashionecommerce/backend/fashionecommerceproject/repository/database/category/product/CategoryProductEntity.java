package project.fashionecommerce.backend.fashionecommerceproject.repository.database.category.product;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import project.fashionecommerce.backend.fashionecommerceproject.repository.auditable.FullAuditable;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Document(value = "category_product")
public class CategoryProductEntity extends FullAuditable implements Serializable {
    @Id
    private String id;
    @Field(targetType = FieldType.OBJECT_ID)
    private String categoryId;
    @Field(targetType = FieldType.OBJECT_ID)
    private String productId;
}

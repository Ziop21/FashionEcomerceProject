package project.fashionecommerce.backend.fashionecommerceproject.repository.database.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import project.fashionecommerce.backend.fashionecommerceproject.repository.auditable.FullAuditable;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Document(value = "category")
public class CategoryEntity extends FullAuditable implements Serializable {
    @Id
    private String id;
    private List<ObjectId> categoryIds;
    private String name;
    private String slug;
    private List<String> images;
}

package project.fashionecommerce.backend.fashionecommerceproject.repository.database.product;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import project.fashionecommerce.backend.fashionecommerceproject.repository.auditable.FullAuditable;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Document(value = "product")
public class ProductEntity extends FullAuditable implements Serializable {
    @Id
    private String id;
    private String name;
    private String slug;
    private String description;
    private Double price;
    private Double promotionalPrice;
    private Long view;
    private Boolean isSelling;
    private List<String> images;
    private Double rating;
    private Boolean isActive;
}

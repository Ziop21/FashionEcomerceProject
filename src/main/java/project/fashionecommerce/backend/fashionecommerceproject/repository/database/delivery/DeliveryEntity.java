package project.fashionecommerce.backend.fashionecommerceproject.repository.database.delivery;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import project.fashionecommerce.backend.fashionecommerceproject.repository.auditable.FullAuditable;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Document(value = "delivery")
public class DeliveryEntity extends FullAuditable implements Serializable {
    @Id
    private String id;
    private String name;
    private String description;
    private Boolean isDeleted;
    private Boolean isActive;
}

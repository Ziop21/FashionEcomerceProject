package project.fashionecomerce.backend.fashionecomerceproject.repository.color;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import project.fashionecomerce.backend.fashionecomerceproject.repository.common.Auditable;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Document(value = "Color")
public class ColorEntity extends Auditable implements Serializable {
    @Id
    private String id;
    private String name;
}

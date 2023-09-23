package project.fashionecomerce.backend.fashionecomerceproject.repository.size;

import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import project.fashionecomerce.backend.fashionecomerceproject.repository.common.Auditable;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Document(value = "Size")
public class SizeEntity extends Auditable implements Serializable{
    @Transient
    public static final String SEQUENCE_NAME = "users_sequence";
    @Id
    private String id;
    private Long sequenceId;
    @Indexed(unique = true)
    private String name;
}

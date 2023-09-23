package project.fashionecomerce.backend.fashionecomerceproject.repository.common;

import lombok.Data;
import java.time.LocalDateTime;


@Data
public abstract class Auditable {
    protected LocalDateTime createdAt;
    protected LocalDateTime updatedAt;

    protected Auditable(){
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        updatedAt = LocalDateTime.now();
    }

}

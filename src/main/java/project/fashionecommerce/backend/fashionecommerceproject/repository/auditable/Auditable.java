package project.fashionecommerce.backend.fashionecommerceproject.repository.auditable;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import java.time.LocalDateTime;

@Data
public abstract class Auditable {
    @CreatedDate
    protected LocalDateTime createdAt;
    @LastModifiedDate
    protected LocalDateTime updatedAt;
    @CreatedBy
    protected String createdBy;
    @LastModifiedBy
    protected String updatedBy;
}

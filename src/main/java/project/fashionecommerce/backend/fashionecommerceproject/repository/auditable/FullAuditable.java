package project.fashionecommerce.backend.fashionecommerceproject.repository.auditable;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

@Data
public abstract class FullAuditable extends TimeAuditable{
    @CreatedBy
    protected String createdBy;
    @LastModifiedBy
    protected String updatedBy;
}

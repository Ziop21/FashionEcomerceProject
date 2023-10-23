package project.fashionecommerce.backend.fashionecommerceproject.repository.auditable;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@Data
public abstract class FullAuditable extends TimeAuditable{
    @CreatedBy
    @Field(targetType = FieldType.OBJECT_ID)
    protected String createdBy;
    @LastModifiedBy
    @Field(targetType = FieldType.OBJECT_ID)
    protected String updatedBy;
}

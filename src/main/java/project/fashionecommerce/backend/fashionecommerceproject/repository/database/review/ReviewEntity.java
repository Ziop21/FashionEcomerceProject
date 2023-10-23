package project.fashionecommerce.backend.fashionecommerceproject.repository.database.review;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import project.fashionecommerce.backend.fashionecommerceproject.repository.auditable.TimeAuditable;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ReviewEntity extends TimeAuditable implements Serializable {
    @Field(targetType = FieldType.OBJECT_ID)
    private String userId;
    private String content;
    private Double rating;
    private List<String> images;

}

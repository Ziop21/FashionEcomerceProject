package project.fashionecomerce.backend.fashionecomerceproject.repository.common;

import lombok.Data;

import java.time.LocalDateTime;


@Data
public abstract class FullAudit extends TimeAudit{
    protected String createdBy;
    protected String updatedBy;

    protected FullAudit(String authorId){
        super();
        if (createdBy == null) {
            createdBy = authorId;
        }
        updatedBy = authorId;
    }

}

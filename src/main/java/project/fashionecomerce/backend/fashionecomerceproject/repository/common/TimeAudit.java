package project.fashionecomerce.backend.fashionecomerceproject.repository.common;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;


@Data
public abstract class TimeAudit {
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    protected LocalDateTime createdAt;
    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    protected LocalDateTime updatedAt;

}

package project.fashionecomerce.backend.fashionecomerceproject.repository.stockDiary;

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
@Document(value = "StockDiary")
public class StockDiaryEntity extends Auditable implements Serializable {
    @Id
    private String stockId;
    private Long quantity;
    private Long errorQuantity;
    private String note;
}

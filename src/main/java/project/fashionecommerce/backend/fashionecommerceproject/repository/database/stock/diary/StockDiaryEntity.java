package project.fashionecommerce.backend.fashionecommerceproject.repository.database.stock.diary;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import project.fashionecommerce.backend.fashionecommerceproject.repository.auditable.FullAuditable;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Document(value = "stock_diary")
public class StockDiaryEntity extends FullAuditable implements Serializable{
    @Id
    private String id;
    private String stockId;
    private Long quantity;
    private Long errorQuantity;
    private String note;
}

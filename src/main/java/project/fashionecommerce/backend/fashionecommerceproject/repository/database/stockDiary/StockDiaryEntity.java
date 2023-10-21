package project.fashionecommerce.backend.fashionecommerceproject.repository.database.stockDiary;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import project.fashionecommerce.backend.fashionecommerceproject.repository.auditable.FullAuditable;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Document(value = "stockDiary")
public class StockDiaryEntity extends FullAuditable implements Serializable{
    @Id
    String id;
    String stockId;
    long quantity;
    long errorQuantity;
    String note;
}

package project.fashionecommerce.backend.fashionecommerceproject.service.database.stock;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.Stock;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.StockId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.StockMapper;
import project.fashionecommerce.backend.fashionecommerceproject.exception.MyConflictsException;
import project.fashionecommerce.backend.fashionecommerceproject.exception.MyResourceNotFoundException;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.stock.StockEntity;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.stock.StockRepository;

@Service
@RequiredArgsConstructor
public class StockCommandService {
    @NonNull final StockRepository stockRepository;
    @NonNull final StockMapper stockMapper;
    public void save(Stock stock) {
        if (stockRepository.existsByProductIdAndSizeIdAndColorId(stock.productId(),
                stock.sizeId(),
                stock.colorId()))
            throw new MyConflictsException();
        StockEntity stockEntity = stockMapper.toEntity(stock);
        stockRepository.save(stockEntity);
    }

    public void delete(StockId stockId) {
        stockRepository.deleteById(stockId.id());
    }

    public void update(StockId stockId, Stock stock) {
        StockEntity foundEntity = stockRepository.findById(stockId.id())
                .orElseThrow(MyResourceNotFoundException::new);
        if (!(foundEntity.getProductId().equals(stock.productId())
                        && foundEntity.getSizeId().equals(stock.sizeId())
                        && foundEntity.getColorId().equals(stock.colorId()))
                && stockRepository.existsByProductIdAndSizeIdAndColorId(stock.productId(),
                stock.sizeId(),
                stock.colorId())
        )
            throw new MyConflictsException();

        stockMapper.updateExisted(stock, foundEntity);
        stockRepository.save(foundEntity);
    }
}

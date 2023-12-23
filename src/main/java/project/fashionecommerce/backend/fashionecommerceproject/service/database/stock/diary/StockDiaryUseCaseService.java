package project.fashionecommerce.backend.fashionecommerceproject.service.database.stock.diary;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.fashionecommerce.backend.fashionecommerceproject.dto.enums.ERole;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.Stock;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.StockId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.StockMapper;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.diary.StockDiary;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.diary.StockDiaryId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.diary.StockDiaryQuery;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.stock.StockCommandService;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.stock.StockQueryService;

@Service
@RequiredArgsConstructor
public class StockDiaryUseCaseService {
    @NonNull
    final StockDiaryCommandService stockDiaryCommandService;
    @NonNull
    final StockCommandService stockCommandService;

    @NonNull
    final StockDiaryQueryService stockDiaryQueryService;
    @NonNull
    final StockQueryService stockQueryService;

    @NonNull
    final StockMapper stockMapper;

    @Transactional
    public void save(StockDiary stockDiary) {
        stockDiaryCommandService.save(stockDiary);
        if (stockDiary.isActive()) {
            Stock foundStock = stockQueryService.findById(new StockId(stockDiary.stockId()));
            foundStock = stockMapper.updateDtoQuantity(foundStock, foundStock.quantity() + stockDiary.quantity());
            stockCommandService.update(new StockId(foundStock.id()), foundStock);
        }
    }
    @Transactional
    public void update(StockDiaryId stockDiaryId, StockDiary newStockDiary) {
        Stock foundStock = stockQueryService.findById(new StockId(newStockDiary.stockId()));
        StockDiary oldStockDiary = stockDiaryQueryService.findById(stockDiaryId);
        stockDiaryCommandService.update(stockDiaryId, newStockDiary);
        if (oldStockDiary.isActive() && !newStockDiary.isActive()){
            foundStock = stockMapper.updateDtoQuantity(foundStock, foundStock.quantity() - oldStockDiary.quantity());
        }
        if (!oldStockDiary.isActive() && newStockDiary.isActive()){
            foundStock = stockMapper.updateDtoQuantity(foundStock, foundStock.quantity() + newStockDiary.quantity());
        }
        if (oldStockDiary.isActive() && newStockDiary.isActive()){
            foundStock = stockMapper.updateDtoQuantity(foundStock, foundStock.quantity() - oldStockDiary.quantity() + newStockDiary.quantity());
        }
        stockCommandService.update(new StockId(foundStock.id()), foundStock);
    }
    @Transactional
    public void delete(StockDiaryId stockDiaryId) {
        StockDiary foundStockDiary = stockDiaryQueryService.findById(stockDiaryId);
        if (foundStockDiary.isActive()) {
            Stock foundStock = stockQueryService.findById(new StockId(foundStockDiary.stockId()));
            foundStock = stockMapper.updateDtoQuantity(foundStock, foundStock.quantity() - foundStockDiary.quantity());
            stockCommandService.update(new StockId(foundStock.id()), foundStock);
        }
        stockDiaryCommandService.delete(stockDiaryId);
    }
    public StockDiary findById(StockDiaryId stockDiaryId) {
        return stockDiaryQueryService.findById(stockDiaryId);
    }
    public Page<StockDiary> findAll(StockDiaryQuery stockDiaryQuery, PageRequest pageRequest) {
        return stockDiaryQueryService.findAll(stockDiaryQuery, pageRequest, ERole.ADMIN);
    }
}

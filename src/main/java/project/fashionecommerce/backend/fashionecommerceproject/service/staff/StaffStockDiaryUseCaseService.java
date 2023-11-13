package project.fashionecommerce.backend.fashionecommerceproject.service.staff;

import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import project.fashionecommerce.backend.fashionecommerceproject.config.security.userDetails.Implement.UserDetailsImpl;
import project.fashionecommerce.backend.fashionecommerceproject.dto.enums.ERole;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.diary.StockDiary;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.diary.StockDiaryId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.diary.StockDiaryMapper;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.diary.StockDiaryQuery;
import project.fashionecommerce.backend.fashionecommerceproject.exception.MyForbiddenException;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.stock.diary.StockDiaryCommandService;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.stock.diary.StockDiaryQueryService;

@Service
@RequiredArgsConstructor
public class StaffStockDiaryUseCaseService {
    @NonNull final StockDiaryCommandService stockDiaryCommandService;
    @NonNull final StockDiaryQueryService stockDiaryQueryService;
    @NonNull final StockDiaryMapper stockDiaryMapper;
    @Transactional
    public void update(StockDiaryId stockDiaryId, StockDiary newStockDiary) {
        StockDiary foundStockDiary = stockDiaryQueryService.findById(stockDiaryId);
        if (foundStockDiary.isDeleted() || foundStockDiary.isActive()) {
            throw new MyForbiddenException();
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        if (!userDetails.getId().equals(foundStockDiary.createdBy())){
            throw new MyForbiddenException();
        }
        foundStockDiary = stockDiaryMapper.updateDto(foundStockDiary, newStockDiary.stockId(), newStockDiary.quantity(),
                newStockDiary.errorQuantity(), newStockDiary.note());
        stockDiaryCommandService.update(stockDiaryId, foundStockDiary);
    }
    @Transactional
    public void delete(StockDiaryId stockDiaryId) {
        StockDiary stockDiary = stockDiaryQueryService.findById(stockDiaryId);
        if (stockDiary.isDeleted() || stockDiary.isActive()) {
            throw new MyForbiddenException();
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        if (!userDetails.getId().equals(stockDiary.createdBy())){
            throw new MyForbiddenException();
        }
        stockDiary = stockDiaryMapper.updateDtoIsDeleted(stockDiary, true);
        stockDiaryCommandService.update(stockDiaryId, stockDiary);
    }
    @Transactional
    public void save(StockDiary stockDiary) {
        stockDiary = stockDiaryMapper.updateDtoIsActive(stockDiary, false);
        stockDiary = stockDiaryMapper.updateDtoIsDeleted(stockDiary, false);
        stockDiaryCommandService.save(stockDiary);
    }

    public Page<StockDiary> findAll(StockDiaryQuery stockDiaryQuery, PageRequest pageRequest) {
        Page<StockDiary> stockDiaryPage = stockDiaryQueryService.findAll(stockDiaryQuery, pageRequest, ERole.STAFF);
        return stockDiaryPage;
    }

    public StockDiary findById(StockDiaryId stockDiaryId) {
        StockDiary stockDiary = stockDiaryQueryService.findById(stockDiaryId);
        if (stockDiary.isDeleted() || stockDiary.isActive()) {
            throw new MyForbiddenException();
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        if (!userDetails.getId().equals(stockDiary.createdBy())){
            throw new MyForbiddenException();
        }
        return stockDiary;
    }
}

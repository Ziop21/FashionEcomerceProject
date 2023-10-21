package project.fashionecommerce.backend.fashionecommerceproject.service.database.stock.diary;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.diary.StockDiary;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.diary.StockDiaryId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.diary.StockDiaryMapper;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stock.diary.StockDiaryQuery;
import project.fashionecommerce.backend.fashionecommerceproject.exception.MyResourceNotFoundException;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.stock.diary.StockDiaryEntity;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.stock.diary.StockDiaryRepository;
@Service
@RequiredArgsConstructor
public class StockDiaryQueryService {
    @NonNull
    final StockDiaryRepository stockDiaryRepository;
    @NonNull
    final StockDiaryMapper stockDiaryMapper;
    public Page<StockDiary> findAll(StockDiaryQuery stockDiaryQuery, PageRequest pageRequest) {
        Page<StockDiaryEntity> stockDiaryEntityPage = stockDiaryRepository.customFindAll(stockDiaryQuery.fromDate(), stockDiaryQuery.toDate(), pageRequest);
        return stockDiaryEntityPage.map(stockDiaryMapper::toDto);
    }

    public StockDiary findById(StockDiaryId stockDiaryId) {
        StockDiaryEntity stockDiaryEntity = stockDiaryRepository.findById(stockDiaryId.id()).orElseThrow(MyResourceNotFoundException::new);
        return stockDiaryMapper.toDto(stockDiaryEntity);
    }
}

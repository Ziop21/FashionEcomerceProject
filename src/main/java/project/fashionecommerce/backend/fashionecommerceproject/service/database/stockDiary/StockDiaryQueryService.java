package project.fashionecommerce.backend.fashionecommerceproject.service.database.stockDiary;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stockDiary.StockDiary;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stockDiary.StockDiaryId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stockDiary.StockDiaryMapper;
import project.fashionecommerce.backend.fashionecommerceproject.dto.stockDiary.StockDiaryQuery;
import project.fashionecommerce.backend.fashionecommerceproject.exception.MyResourceNotFoundException;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.stockDiary.StockDiaryEntity;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.stockDiary.StockDiaryRepository;
@Service
@RequiredArgsConstructor
public class StockDiaryQueryService {
    @NonNull
    final StockDiaryRepository stockDiaryRepository;
    @NonNull
    final StockDiaryMapper stockDiaryMapper;
    public Page<StockDiary> findAll(StockDiaryQuery stockDiaryQuery, PageRequest pageRequest) {
        Page<StockDiaryEntity> stockDiaryEntityPage = StockDiaryRepository.customerFindAll(stockDiaryQuery.search(), pageRequest);
        return stockDiaryEntityPage.map(stockDiaryMapper::toDto);
    }

    public StockDiary findById(StockDiaryId stockDiaryId) {
        StockDiaryEntity stockDiaryEntity = stockDiaryRepository.findById(stockDiaryId.id()).orElseThrow(MyResourceNotFoundException::new);
        return stockDiaryMapper.toDto(stockDiaryEntity);
    }
}

package project.fashionecomerce.backend.fashionecomerceproject.service.stockDiary;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.fashionecomerce.backend.fashionecomerceproject.dto.stockDiary.StockDiary;
import project.fashionecomerce.backend.fashionecomerceproject.dto.stockDiary.StockDiaryMapper;
import project.fashionecomerce.backend.fashionecomerceproject.exception.MyConflictsException;
import project.fashionecomerce.backend.fashionecomerceproject.repository.stockDiary.StockDiaryEntity;
import project.fashionecomerce.backend.fashionecomerceproject.repository.stockDiary.StockDiaryRepository;

@Service
@RequiredArgsConstructor
public class StockDiaryCommandService {
    @NonNull
    final StockDiaryRepository stockDiaryRepository;
    @NonNull
    final StockDiaryMapper stockDiaryMapper;
    public void save(StockDiary stockDiary) {
        if (stockDiaryRepository.existsByName(stockDiary.id()))
            throw new MyConflictsException();
        StockDiaryEntity stockDiaryEntity = stockDiaryMapper.toEntity(stockDiary);
        stockDiaryRepository.save(stockDiaryEntity);
    }
}

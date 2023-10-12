package project.fashionecomerce.backend.fashionecomerceproject.controller.stockDiary;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import project.fashionecomerce.backend.fashionecomerceproject.controller.stockDiary.models.StockDiaryRequest;

@RequestMapping("/stockDiary")
public interface StockDiaryAPI {
    @PostMapping
    Void save(@RequestBody @Valid StockDiaryRequest stockDiaryRequest);
}

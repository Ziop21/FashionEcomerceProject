package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.stockDiary;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.stockDiary.models.StockDiaryRequest;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.stockDiary.models.StockDiaryResponse;

@RequestMapping("/api/admin/stockDiary/{stockDiaryId}")
public interface StockDiaryAPI {
    @PutMapping
    void update(@PathVariable String stockDiaryId, @RequestBody @Valid StockDiaryRequest stockDiaryRequest);

    @GetMapping
    ResponseEntity<StockDiaryResponse> findById(@PathVariable String stockDiaryId);

    @DeleteMapping
    void delete(@PathVariable String stockDiaryId);
}

package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.stock.diary;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.stock.diary.models.StockDiaryResponse;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.stock.diary.models.StockDiaryRequest;

@RequestMapping("/api/admin/stock-diary/{stockDiaryId}")
public interface StockDiaryAPI {
    @PutMapping
    void update(@PathVariable String stockDiaryId, @RequestBody @Valid StockDiaryRequest stockDiaryRequest);

    @GetMapping
    ResponseEntity<StockDiaryResponse> findById(@PathVariable String stockDiaryId);

    @DeleteMapping
    void delete(@PathVariable String stockDiaryId);
}

package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.stock.diary;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.stock.diary.models.StockDiaryRequest;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.stock.diary.models.StockDiaryResponse;

@RequestMapping("/api/admin/manager/stock-diary/{stockDiaryId}")
public interface StockDiaryAPI {
    @PutMapping
    void update(@PathVariable String stockDiaryId, @RequestBody @Valid StockDiaryRequest stockDiaryRequest);

    @GetMapping
    ResponseEntity<StockDiaryResponse> findById(@PathVariable String stockDiaryId);

    @DeleteMapping
    void delete(@PathVariable String stockDiaryId);
}

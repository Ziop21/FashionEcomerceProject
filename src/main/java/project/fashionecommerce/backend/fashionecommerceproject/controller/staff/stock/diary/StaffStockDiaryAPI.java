package project.fashionecommerce.backend.fashionecommerceproject.controller.staff.stock.diary;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import project.fashionecommerce.backend.fashionecommerceproject.controller.staff.stock.diary.models.StaffStockDiaryRequest;
import project.fashionecommerce.backend.fashionecommerceproject.controller.staff.stock.diary.models.StaffStockDiaryResponse;

@RequestMapping("/api/staff/stock-diary/{stockDiaryId}")
public interface StaffStockDiaryAPI {
    @PutMapping
    void update(@PathVariable String stockDiaryId, @RequestBody @Valid StaffStockDiaryRequest staffStockDiaryRequest);

    @GetMapping
    ResponseEntity<StaffStockDiaryResponse> findById(@PathVariable String stockDiaryId);

    @DeleteMapping
    void delete(@PathVariable String stockDiaryId);
}

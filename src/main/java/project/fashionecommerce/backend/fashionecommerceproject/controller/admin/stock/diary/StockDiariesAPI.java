package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.stock.diary;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.fashionecommerce.backend.fashionecommerceproject.common.models.PageResponse;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.stock.diary.models.StockDiaryResponse;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.stock.diary.models.StockDiaryRequest;

import java.time.LocalDateTime;

@RequestMapping("/api/admin/stock-diary")
public interface StockDiariesAPI {
    @PostMapping
    void save(@RequestBody @Valid StockDiaryRequest stockDiaryRequest);
    @GetMapping
    ResponseEntity<PageResponse<StockDiaryResponse>> findAll(
            @RequestParam(required = false, value = "fromDate", defaultValue = "") LocalDateTime fromDate,
            @RequestParam(required = false, value = "toDate", defaultValue = "") LocalDateTime toDate,
            @RequestParam(required = false, value = "sort", defaultValue = "") String sort,
            @RequestParam(required = false, value = "pageCurrent", defaultValue = "1") @Min(1) Integer pageCurrent,
            @RequestParam(required = false, value = "pageSize", defaultValue = "20") Integer pageSize
    );
}

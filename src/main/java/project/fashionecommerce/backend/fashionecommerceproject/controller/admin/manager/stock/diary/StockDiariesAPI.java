package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.stock.diary;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.fashionecommerce.backend.fashionecommerceproject.common.models.PageResponse;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.stock.diary.models.StockDiaryRequest;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.stock.diary.models.StockDiaryResponse;

import java.time.LocalDate;

@RequestMapping("/api/admin/manager/stock-diary")
public interface StockDiariesAPI {
    @PostMapping
    void save(@RequestBody @Valid StockDiaryRequest stockDiaryRequest);
    @GetMapping
    ResponseEntity<PageResponse<StockDiaryResponse>> findAll(
            @RequestParam(required = false, value = "search", defaultValue = "") String search,
            @RequestParam(required = false, value = "fromDate", defaultValue = "") LocalDate fromDate,
            @RequestParam(required = false, value = "toDate", defaultValue = "") LocalDate toDate,
            @RequestParam(required = false, value = "sort", defaultValue = "") String sort,
            @RequestParam(required = false, value = "pageCurrent", defaultValue = "1") @Min(1) Integer pageCurrent,
            @RequestParam(required = false, value = "pageSize", defaultValue = "20") Integer pageSize
    );
}

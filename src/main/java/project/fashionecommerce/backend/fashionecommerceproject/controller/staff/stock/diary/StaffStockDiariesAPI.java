package project.fashionecommerce.backend.fashionecommerceproject.controller.staff.stock.diary;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.fashionecommerce.backend.fashionecommerceproject.common.models.PageResponse;
import project.fashionecommerce.backend.fashionecommerceproject.controller.staff.stock.diary.models.StaffStockDiaryRequest;
import project.fashionecommerce.backend.fashionecommerceproject.controller.staff.stock.diary.models.StaffStockDiaryResponse;
import java.time.LocalDate;

@RequestMapping("/api/staff/stock-diaries")
public interface StaffStockDiariesAPI {
    @PostMapping
    void save(@RequestBody @Valid StaffStockDiaryRequest staffStockDiaryRequest);
    @GetMapping
    ResponseEntity<PageResponse<StaffStockDiaryResponse>> findAll(
            @RequestParam(required = false, value = "search", defaultValue = "") String search,
            @RequestParam(required = false, value = "fromDate", defaultValue = "") LocalDate fromDate,
            @RequestParam(required = false, value = "toDate", defaultValue = "") LocalDate toDate,
            @RequestParam(required = false, value = "sort", defaultValue = "") String sort,
            @RequestParam(required = false, value = "pageCurrent", defaultValue = "1") @Min(1) Integer pageCurrent,
            @RequestParam(required = false, value = "pageSize", defaultValue = "20") Integer pageSize
    );
}

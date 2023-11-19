package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.color;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.fashionecommerce.backend.fashionecommerceproject.common.models.PageResponse;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.color.models.ColorRequest;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.color.models.ColorResponse;

@RequestMapping("/api/admin/manager/color")
public interface ColorsAPI {
    @PostMapping
    void save(@RequestBody @Valid ColorRequest colorRequest);
    @GetMapping
    ResponseEntity<PageResponse<ColorResponse>> findAll(
            @RequestParam(required = false,value = "search", defaultValue = "") String search,
            @RequestParam(required = false,value = "sort", defaultValue = "") String sort,
            @RequestParam(required = false,value = "currentPage", defaultValue = "1") @Min(1) Integer currentPage,
            @RequestParam(required = false,value = "pageSize",defaultValue = "20") Integer pageSize
    );
}

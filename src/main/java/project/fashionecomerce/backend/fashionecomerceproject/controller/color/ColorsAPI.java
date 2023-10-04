package project.fashionecomerce.backend.fashionecomerceproject.controller.color;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.fashionecomerce.backend.fashionecomerceproject.controller.color.models.ColorRequest;
import project.fashionecomerce.backend.fashionecomerceproject.controller.color.models.ColorResponse;
import project.fashionecomerce.backend.fashionecomerceproject.controller.common.PageResponse;

@RequestMapping("/color")
public interface ColorsAPI {
    @PostMapping
    ResponseEntity<ColorResponse> save(@RequestBody @Valid ColorRequest colorRequest);
    @GetMapping
    ResponseEntity<PageResponse<ColorResponse>> findAll(
            @RequestParam(required = false,value = "searchString", defaultValue = "") String searchString,
            @RequestParam(required = false,value = "sort", defaultValue = "") String sort,
            @RequestParam(required = false,value = "pageCurrent", defaultValue = "1") @Min(1) Integer pageCurrent,
            @RequestParam(required = false,value = "pageSize",defaultValue = "20") Integer pageSize
    );
}

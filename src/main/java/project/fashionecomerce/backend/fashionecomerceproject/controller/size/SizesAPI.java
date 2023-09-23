package project.fashionecomerce.backend.fashionecomerceproject.controller.size;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.fashionecomerce.backend.fashionecomerceproject.controller.common.PageResponse;
import project.fashionecomerce.backend.fashionecomerceproject.controller.size.models.SizeRequest;
import project.fashionecomerce.backend.fashionecomerceproject.controller.size.models.SizeResponse;

@RequestMapping("/size")
public interface SizesAPI {
    @PostMapping
    ResponseEntity<SizeResponse> save(@RequestBody @Valid SizeRequest sizeRequest);
//    @GetMapping
//    ResponseEntity<List<SizeResponse>> findAll();

    @GetMapping
    ResponseEntity<PageResponse<SizeResponse>> findAll(
            @RequestParam(required = false, value = "searchString", defaultValue = "") String searchString,
            @RequestParam(required = false, value = "sort", defaultValue = "") String sort,
            @RequestParam(required = false, value = "pageCurrent", defaultValue = "1") @Min(1) Integer pageCurrent,
            @RequestParam(required = false, value = "pageSize", defaultValue = "20") Integer pageSize
            );
}

package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.user.level;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.fashionecommerce.backend.fashionecommerceproject.common.models.PageResponse;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.user.level.models.UserLevelRequest;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.user.level.models.UserLevelResponse;

@RequestMapping("/api/admin/user-level")
public interface UserLevelsAPI {
    @PostMapping
    void save(@RequestBody @Valid UserLevelRequest userLevelRequest);

    @GetMapping
    ResponseEntity<PageResponse<UserLevelResponse>> findAll(
            @RequestParam(required = false, value = "searchString", defaultValue = "") String searchString,
            @RequestParam(required = false, value = "sort", defaultValue = "") String sort,
            @RequestParam(required = false, value = "pageCurrent", defaultValue = "1") @Min(1) Integer pageCurrent,
            @RequestParam(required = false, value = "pageSize", defaultValue = "20") Integer pageSize
    );
}

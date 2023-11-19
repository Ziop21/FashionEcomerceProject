package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.user;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.fashionecommerce.backend.fashionecommerceproject.common.models.PageResponse;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.user.models.UserRequest;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.user.models.UserResponse;

import java.time.LocalDate;
import java.util.List;

@RequestMapping("/api/admin/manager/user")
public interface UsersAPI {
    @PostMapping
    void save(@RequestBody @Valid UserRequest userRequest);

    @GetMapping
    ResponseEntity<PageResponse<UserResponse>> findAll(
            @RequestParam(required = false, value = "search", defaultValue = "") String search,
            @RequestParam(required = false, value = "isEmailActive", defaultValue = "") Boolean isEmailActive,
            @RequestParam(required = false, value = "isDeleted", defaultValue = "") Boolean isDeleted,
            @RequestParam(required = false, value = "isPhoneActive", defaultValue = "") Boolean isPhoneActive,
            @RequestParam(required = false, value = "userLevelIds", defaultValue = "") List<String> userLevelIds,
            @RequestParam(required = false, value = "fromDate", defaultValue = "") LocalDate fromDate,
            @RequestParam(required = false, value = "toDate", defaultValue = "") LocalDate toDate,
            @RequestParam(required = false, value = "sort", defaultValue = "") String sort,
            @RequestParam(required = false, value = "currentPage", defaultValue = "1") @Min(1) Integer currentPage,
            @RequestParam(required = false, value = "pageSize", defaultValue = "20") Integer pageSize
    );
}

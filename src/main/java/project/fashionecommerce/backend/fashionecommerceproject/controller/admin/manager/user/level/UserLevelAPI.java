package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.user.level;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.user.level.models.UserLevelRequest;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.user.level.models.UserLevelResponse;

@RequestMapping("/api/admin/manager/user-level/{userLevelId}")
public interface UserLevelAPI {
    @PutMapping
    void update(@PathVariable String userLevelId, @RequestBody @Valid UserLevelRequest userLevelRequest);

    @GetMapping
    ResponseEntity<UserLevelResponse> findById(@PathVariable String userLevelId);

    @DeleteMapping
    void delete(@PathVariable String userLevelId);
}

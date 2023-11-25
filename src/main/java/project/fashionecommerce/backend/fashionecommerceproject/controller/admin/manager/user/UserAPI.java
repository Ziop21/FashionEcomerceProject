package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.user;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.user.models.UserRequest;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.user.models.UserResponse;

@RequestMapping("/api/admin/manager/user/{userId}")
public interface UserAPI {
    @GetMapping
    ResponseEntity<UserResponse> findById(@PathVariable String userId);

    @DeleteMapping
    void delete(@PathVariable String userId);

    @PutMapping
    void update(@PathVariable String userId, @RequestBody @Valid UserRequest userRequest);
}

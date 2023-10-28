package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.follow;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.follow.models.FollowRequest;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.follow.models.FollowResponse;

@RequestMapping("/api/admin/follow/{followId}")
public interface FollowAPI {
    @PutMapping
    void update(@PathVariable String followId, @RequestBody @Valid FollowRequest followRequest);

    @GetMapping
    ResponseEntity<FollowResponse> findById(@PathVariable String followId);

    @DeleteMapping
    void delete(@PathVariable String followId);
}

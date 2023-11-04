package project.fashionecommerce.backend.fashionecommerceproject.controller.customer.follow;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.fashionecommerce.backend.fashionecommerceproject.controller.customer.follow.models.CustomerFollowResponse;
import java.util.List;

@RequestMapping("/api/customer/follow")
public interface CustomerFollowAPI {
    @PostMapping("/{productId}")
    void save(@PathVariable String productId);

    @GetMapping
    ResponseEntity<List<CustomerFollowResponse>> findAll(
            @RequestParam(required = false,value = "search", defaultValue = "") String search);

    @DeleteMapping("/{followId}")
    void delete(@PathVariable String followId);
}

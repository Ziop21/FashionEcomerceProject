package project.fashionecommerce.backend.fashionecommerceproject.controller.customer.user;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import project.fashionecommerce.backend.fashionecommerceproject.controller.customer.user.models.CustomerPasswordRequest;
import project.fashionecommerce.backend.fashionecommerceproject.controller.customer.user.models.CustomerUserRequest;
import project.fashionecommerce.backend.fashionecommerceproject.controller.customer.user.models.CustomerUserResponse;

@RequestMapping("/api/customer/user")
public interface CustomerUserAPI {
    @GetMapping
    ResponseEntity<CustomerUserResponse> findById();

    @PutMapping
    void update(@RequestBody @Valid CustomerUserRequest customerUserRequest);
    @PutMapping("/password")
    void updatePassword(@RequestBody @Valid CustomerPasswordRequest customerPasswordRequest);

    @PutMapping("/delete")
    void delete(@RequestBody @Valid CustomerPasswordRequest customerPasswordRequest);
}

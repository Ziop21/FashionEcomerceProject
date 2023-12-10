package project.fashionecommerce.backend.fashionecommerceproject.controller.guest.color;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import project.fashionecommerce.backend.fashionecommerceproject.controller.guest.color.models.GuestColorResponse;

@RequestMapping("/api/guest/color")
public interface GuestColorAPI {
    @GetMapping("/{colorId}")
    ResponseEntity<GuestColorResponse> findById(@PathVariable String colorId);
}

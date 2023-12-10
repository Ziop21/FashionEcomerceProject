package project.fashionecommerce.backend.fashionecommerceproject.controller.guest.size;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import project.fashionecommerce.backend.fashionecommerceproject.controller.guest.size.models.GuestSizeResponse;

@RequestMapping("/api/guest/size")
public interface GuestSizeAPI {
    @GetMapping("/{sizeId}")
    ResponseEntity<GuestSizeResponse> findById(@PathVariable String sizeId);
}

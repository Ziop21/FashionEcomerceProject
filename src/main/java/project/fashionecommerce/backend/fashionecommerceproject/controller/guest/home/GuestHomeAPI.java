package project.fashionecommerce.backend.fashionecommerceproject.controller.guest.home;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/guest")
public interface GuestHomeAPI {
    @GetMapping
    ResponseEntity<?> index(HttpServletRequest request);
}

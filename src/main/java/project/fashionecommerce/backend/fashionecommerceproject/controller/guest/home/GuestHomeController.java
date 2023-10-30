package project.fashionecommerce.backend.fashionecommerceproject.controller.guest.home;

import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import project.fashionecommerce.backend.fashionecommerceproject.controller.guest.home.models.UserInfoResponse;
import project.fashionecommerce.backend.fashionecommerceproject.dto.authen.MyAuthentication;
import project.fashionecommerce.backend.fashionecommerceproject.service.guest.HomeUseCaseService;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class GuestHomeController implements GuestHomeAPI {
    @NonNull final HomeUseCaseService homeUseCaseService;

    @Override
    public ResponseEntity<?> index(HttpServletRequest request) {
        MyAuthentication myAuthentication = homeUseCaseService.index(request);
        if (myAuthentication.userDetails() == null) {
            if (myAuthentication.cartTokenCookieString() == null)
                return ResponseEntity.ok().body("Home page");
            return ResponseEntity
                    .ok()
                    .header(HttpHeaders.SET_COOKIE, myAuthentication.cartTokenCookieString())
                    .body("Home page");
        }
        List<String> roles = myAuthentication.userDetails().getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        return ResponseEntity
                .ok()
                .body(new UserInfoResponse(myAuthentication.userDetails().getId(),
                        myAuthentication.userDetails().getUsername(),
                        myAuthentication.userDetails().getEmail(),
                        roles));
    }

}

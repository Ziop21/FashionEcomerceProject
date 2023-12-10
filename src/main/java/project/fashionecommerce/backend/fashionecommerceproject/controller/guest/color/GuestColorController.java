package project.fashionecommerce.backend.fashionecommerceproject.controller.guest.color;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import project.fashionecommerce.backend.fashionecommerceproject.controller.guest.color.models.GuestColorModelMapper;
import project.fashionecommerce.backend.fashionecommerceproject.controller.guest.color.models.GuestColorResponse;
import project.fashionecommerce.backend.fashionecommerceproject.dto.color.Color;
import project.fashionecommerce.backend.fashionecommerceproject.dto.color.ColorId;
import project.fashionecommerce.backend.fashionecommerceproject.service.guest.GuestColorUseCaseService;

@RestController
@RequiredArgsConstructor
public class GuestColorController implements GuestColorAPI {
    @NonNull
    final GuestColorUseCaseService guestColorUseCaseService;
    @NonNull
    final GuestColorModelMapper guestColorModelMapper;
    @Override
    public ResponseEntity<GuestColorResponse> findById(String colorId) {
        Color color = guestColorUseCaseService.findById(new ColorId(colorId));
        return new ResponseEntity<>(guestColorModelMapper.toModel(color), HttpStatus.OK);
    }
}

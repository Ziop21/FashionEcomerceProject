package project.fashionecommerce.backend.fashionecommerceproject.controller.guest.size;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import project.fashionecommerce.backend.fashionecommerceproject.controller.guest.size.models.GuestSizeModelMapper;
import project.fashionecommerce.backend.fashionecommerceproject.controller.guest.size.models.GuestSizeResponse;
import project.fashionecommerce.backend.fashionecommerceproject.dto.size.Size;
import project.fashionecommerce.backend.fashionecommerceproject.dto.size.SizeId;
import project.fashionecommerce.backend.fashionecommerceproject.service.guest.GuestSizeUseCaseService;

@RestController
@RequiredArgsConstructor
public class GuestSizeController implements GuestSizeAPI {
    @NonNull
    final GuestSizeUseCaseService guestSizeUseCaseService;
    @NonNull
    final GuestSizeModelMapper guestSizeModelMapper;
    @Override
    public ResponseEntity<GuestSizeResponse> findById(String sizeId) {
        Size size = guestSizeUseCaseService.findById(new SizeId(sizeId));
        return new ResponseEntity<>(guestSizeModelMapper.toModel(size), HttpStatus.OK);
    }
}

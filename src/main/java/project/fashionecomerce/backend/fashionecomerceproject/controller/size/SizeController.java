package project.fashionecomerce.backend.fashionecomerceproject.controller.size;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import project.fashionecomerce.backend.fashionecomerceproject.controller.size.models.SizeModelMapper;
import project.fashionecomerce.backend.fashionecomerceproject.controller.size.models.SizeRequest;
import project.fashionecomerce.backend.fashionecomerceproject.controller.size.models.SizeResponse;
import project.fashionecomerce.backend.fashionecomerceproject.dto.size.Size;
import project.fashionecomerce.backend.fashionecomerceproject.dto.size.SizeId;
import project.fashionecomerce.backend.fashionecomerceproject.service.size.SizeUseCaseService;

@RestController
@RequiredArgsConstructor
public class SizeController implements SizeAPI{
    @NonNull
    final SizeUseCaseService sizeUseCaseService;
    @NonNull
    final SizeModelMapper sizeModelMapper;
    @Override
    public void update(String sizeId, SizeRequest sizeRequest) {
        Size size = sizeModelMapper.toDto(sizeRequest);
        sizeUseCaseService.update(new SizeId(sizeId), size);
    }

    @Override
    public ResponseEntity<SizeResponse> findById(String sizeId) {
        Size size = sizeUseCaseService.findById(new SizeId(sizeId));
        SizeResponse sizeResponse = sizeModelMapper.toModel(size);
        return new ResponseEntity<>(sizeResponse, HttpStatus.OK);
    }

    @Override
    public void delete(String sizeId) {
        sizeUseCaseService.delete(new SizeId(sizeId));
    }
}

package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.color;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.color.models.ColorModelMapper;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.color.models.ColorRequest;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.color.models.ColorResponse;
import project.fashionecommerce.backend.fashionecommerceproject.dto.color.Color;
import project.fashionecommerce.backend.fashionecommerceproject.dto.color.ColorId;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.color.ColorUseCaseService;

@RestController
@RequiredArgsConstructor
public class ColorController implements ColorAPI {
    @NonNull
    final ColorUseCaseService colorUseCaseService;
    @NonNull
    final ColorModelMapper colorModelMapper;
    @Override
    public void update(String colorId, ColorRequest colorRequest) {
        Color color = colorModelMapper.toDto(colorRequest);
        colorUseCaseService.update(new ColorId(colorId),color);
    }
    @Override
    public ResponseEntity<ColorResponse> findById(String colorId) {
        Color color = colorUseCaseService.findById(new ColorId(colorId));
        ColorResponse colorResponse = colorModelMapper.toModel(color);
        return new ResponseEntity<>(colorResponse,HttpStatus.OK);
    }
    @Override
    public void delete(String colorId) {
        colorUseCaseService.delete(new ColorId(colorId));
    }
}

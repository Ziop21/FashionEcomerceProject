package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.color;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import project.fashionecommerce.backend.fashionecommerceproject.common.models.PageResponse;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.color.models.ColorModelMapper;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.color.models.ColorRequest;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.color.models.ColorResponse;
import project.fashionecommerce.backend.fashionecommerceproject.dto.color.Color;
import project.fashionecommerce.backend.fashionecommerceproject.dto.color.ColorQuery;
import project.fashionecommerce.backend.fashionecommerceproject.dto.handler.MySortHandler;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.color.ColorUseCaseService;

@RestController
@RequiredArgsConstructor
public class ColorsController implements ColorsAPI {

    @NonNull
    final ColorUseCaseService colorUseCaseService;
    @NonNull
    final ColorModelMapper colorModelMapper;
    @Override
    public void save(ColorRequest colorRequest) {
        Color color = colorModelMapper.toDto(colorRequest);
        colorUseCaseService.save(color);
    }
    @Override
    public ResponseEntity<PageResponse<ColorResponse>> findAll(String search, String sort, Integer pageCurrent, Integer pageSize) {
       ColorQuery colorQuery = new ColorQuery(search);

       PageRequest pageRequest = PageRequest.of(pageCurrent-1, pageSize, MySortHandler.of(sort));

       Page<Color>  colorPage = colorUseCaseService.findAll(colorQuery, pageRequest);

       PageResponse<ColorResponse> colorResponsePage = new PageResponse<>(colorPage.map(colorModelMapper::toModel));
       return new ResponseEntity<>(colorResponsePage,HttpStatus.OK);
    }
}

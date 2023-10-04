package project.fashionecomerce.backend.fashionecomerceproject.controller.color;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import project.fashionecomerce.backend.fashionecomerceproject.controller.color.models.ColorModelMapper;
import project.fashionecomerce.backend.fashionecomerceproject.controller.color.models.ColorRequest;
import project.fashionecomerce.backend.fashionecomerceproject.controller.color.models.ColorResponse;
import project.fashionecomerce.backend.fashionecomerceproject.controller.common.PageResponse;
import project.fashionecomerce.backend.fashionecomerceproject.dto.color.Color;
import project.fashionecomerce.backend.fashionecomerceproject.dto.color.ColorQuery;
import project.fashionecomerce.backend.fashionecomerceproject.dto.common.MySortHandler;
import project.fashionecomerce.backend.fashionecomerceproject.service.color.ColorUseCaseService;

@RestController
@RequiredArgsConstructor
public class ColorsController implements ColorsAPI {

    @NonNull
    final ColorUseCaseService colorUseCaseService;
    @NonNull
    final ColorModelMapper colorModelMapper;
    @Override
    public ResponseEntity<ColorResponse> save(ColorRequest colorRequest) {
        Color color = colorModelMapper.toDto(colorRequest);
        colorUseCaseService.save(color);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }
    @Override
    public ResponseEntity<PageResponse<ColorResponse>> findAll(String searchString, String sort, Integer pageCurrent, Integer pageSize) {
       ColorQuery colorQuery = new ColorQuery(searchString);

       PageRequest pageRequest = PageRequest.of(pageCurrent-1,pageSize, MySortHandler.of(sort));

       Page<Color>  colorPage = colorUseCaseService.findAll(colorQuery, pageRequest);

       PageResponse<ColorResponse> colorResponsePage = new PageResponse<>(colorPage.map(colorModelMapper::toModel));
       return new ResponseEntity<PageResponse<ColorResponse>>(colorResponsePage,HttpStatus.OK);
    }
}

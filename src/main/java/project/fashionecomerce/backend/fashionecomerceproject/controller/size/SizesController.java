package project.fashionecomerce.backend.fashionecomerceproject.controller.size;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import project.fashionecomerce.backend.fashionecomerceproject.controller.common.PageResponse;
import project.fashionecomerce.backend.fashionecomerceproject.controller.size.models.SizeModelMapper;
import project.fashionecomerce.backend.fashionecomerceproject.controller.size.models.SizeRequest;
import project.fashionecomerce.backend.fashionecomerceproject.controller.size.models.SizeResponse;
import project.fashionecomerce.backend.fashionecomerceproject.dto.common.MySortHandler;
import project.fashionecomerce.backend.fashionecomerceproject.dto.size.Size;
import project.fashionecomerce.backend.fashionecomerceproject.dto.size.SizeQuery;
import project.fashionecomerce.backend.fashionecomerceproject.service.generator.SequenceGeneratorService;
import project.fashionecomerce.backend.fashionecomerceproject.service.size.SizeUseCaseService;

@RestController
@RequiredArgsConstructor
public class SizesController implements SizesAPI {
    @NonNull
    final SizeUseCaseService sizeUseCaseService;
    @NonNull
    final SizeModelMapper sizeModelMapper;
    @NonNull
    final SequenceGeneratorService sequenceGeneratorService;
    @Override
    public ResponseEntity<SizeResponse> save(SizeRequest sizeRequest) {
        Size size = sizeModelMapper.toDto(sizeRequest);
        sizeUseCaseService.save(size);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<PageResponse<SizeResponse>> findAll(String searchString, String sort, Integer pageCurrent, Integer pageSize) {
        SizeQuery sizeQuery = new SizeQuery(searchString);
        PageRequest pageRequest = PageRequest.of(pageCurrent - 1, pageSize, MySortHandler.of(sort));

        Page<Size> sizePage = sizeUseCaseService.findAll(sizeQuery, pageRequest);

        PageResponse<SizeResponse> sizeResponsePage = new PageResponse<>(sizePage.map(sizeModelMapper::toModel));
        return new ResponseEntity<>(sizeResponsePage, HttpStatus.OK);
    }

//    @Override
//    public ResponseEntity<List<SizeResponse>> findAll() {
//        List<Size> sizeList = sizeUseCaseService.findAll();
//        List<SizeResponse> sizeResponseList = sizeModelMapper.toModel(sizeList);
//        return new ResponseEntity<>(sizeResponseList, HttpStatus.OK);
//    }
}

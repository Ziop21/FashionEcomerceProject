package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.size;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import project.fashionecommerce.backend.fashionecommerceproject.common.models.PageResponse;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.size.models.SizeModelMapper;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.size.models.SizeRequest;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.size.models.SizeResponse;
import project.fashionecommerce.backend.fashionecommerceproject.dto.handler.MySortHandler;
import project.fashionecommerce.backend.fashionecommerceproject.dto.size.Size;
import project.fashionecommerce.backend.fashionecommerceproject.dto.size.SizeQuery;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.size.SizeUseCaseService;

@RestController
@RequiredArgsConstructor
public class SizesController implements SizesAPI {
    @NonNull
    final SizeUseCaseService sizeUseCaseService;
    @NonNull
    final SizeModelMapper sizeModelMapper;
    @Override
    public void save(SizeRequest sizeRequest) {
        Size size = sizeModelMapper.toDto(sizeRequest);
        sizeUseCaseService.save(size);
    }

    @Override
    public ResponseEntity<PageResponse<SizeResponse>> findAll(String search, String sort, Integer currentPage, Integer pageSize) {
        SizeQuery sizeQuery = new SizeQuery(search);
        PageRequest pageRequest = PageRequest.of(currentPage - 1, pageSize, MySortHandler.of(sort));

        Page<Size> sizePage = sizeUseCaseService.findAll(sizeQuery, pageRequest);

        PageResponse<SizeResponse> sizeResponsePage = new PageResponse<>(sizePage.map(sizeModelMapper::toModel));
        return new ResponseEntity<>(sizeResponsePage, HttpStatus.OK);
    }
}

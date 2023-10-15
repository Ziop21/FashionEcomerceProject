package project.fashionecommerce.backend.fashionecommerceproject.service.database.size;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.fashionecommerce.backend.fashionecommerceproject.dto.size.Size;
import project.fashionecommerce.backend.fashionecommerceproject.dto.size.SizeId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.size.SizeQuery;

@Service
@RequiredArgsConstructor
public class SizeUseCaseService {
    @NonNull
    final SizeCommandService sizeCommandService;
    @NonNull
    final SizeQueryService sizeQueryService;
    @Transactional
    public void save(Size size) {
        sizeCommandService.save(size);
    }

    @Transactional
    public void update(SizeId sizeId, Size size) {
        sizeCommandService.update(sizeId, size);
    }
    @Transactional
    public void delete(SizeId sizeId) {
        sizeCommandService.delete(sizeId);
    }

    public Size findById(SizeId sizeId) {
        return sizeQueryService.findById(sizeId);
    }

    public Page<Size> findAll(SizeQuery sizeQuery, PageRequest pageRequest) {
        return sizeQueryService.findAll(sizeQuery, pageRequest);
    }
}

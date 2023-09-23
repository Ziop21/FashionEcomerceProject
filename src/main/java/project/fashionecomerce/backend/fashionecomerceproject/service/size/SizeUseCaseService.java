package project.fashionecomerce.backend.fashionecomerceproject.service.size;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.fashionecomerce.backend.fashionecomerceproject.dto.size.Size;
import project.fashionecomerce.backend.fashionecomerceproject.dto.size.SizeId;
import project.fashionecomerce.backend.fashionecomerceproject.dto.size.SizeQuery;

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
//    public List<Size> findAll() {
//        return sizeQueryService.findAll();
//    }

    public Size findById(SizeId sizeId) {
        return sizeQueryService.findById(sizeId);
    }

    public Page<Size> findAll(SizeQuery sizeQuery, PageRequest pageRequest) {
        return sizeQueryService.findAll(sizeQuery, pageRequest);
    }
}

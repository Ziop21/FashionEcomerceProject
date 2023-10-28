
package project.fashionecommerce.backend.fashionecommerceproject.service.database.size;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import project.fashionecommerce.backend.fashionecommerceproject.dto.size.Size;
import project.fashionecommerce.backend.fashionecommerceproject.dto.size.SizeId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.size.SizeMapper;
import project.fashionecommerce.backend.fashionecommerceproject.dto.size.SizeQuery;
import project.fashionecommerce.backend.fashionecommerceproject.exception.MyResourceNotFoundException;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.size.SizeEntity;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.size.SizeRepository;

@Service
@RequiredArgsConstructor
public class SizeQueryService {
    @NonNull
    final SizeRepository sizeRepository;
    @NonNull
    final SizeMapper sizeMapper;

    public Size findById(SizeId sizeId) {
        SizeEntity sizeEntity = sizeRepository.findById(sizeId.id())
                .orElseThrow(MyResourceNotFoundException::new);
        return sizeMapper.toDto(sizeEntity);
    }

    public Page<Size> findAll(SizeQuery sizeQuery, PageRequest pageRequest) {
        Page<SizeEntity> sizeEntityPage = sizeRepository.customFindAll(sizeQuery.search(), pageRequest);
        return sizeEntityPage.map(sizeMapper::toDto);
    }
}

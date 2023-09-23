
package project.fashionecomerce.backend.fashionecomerceproject.service.size;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import project.fashionecomerce.backend.fashionecomerceproject.dto.size.Size;
import project.fashionecomerce.backend.fashionecomerceproject.dto.size.SizeId;
import project.fashionecomerce.backend.fashionecomerceproject.dto.size.SizeMapper;
import project.fashionecomerce.backend.fashionecomerceproject.dto.size.SizeQuery;
import project.fashionecomerce.backend.fashionecomerceproject.repository.size.SizeEntity;
import project.fashionecomerce.backend.fashionecomerceproject.repository.size.SizeRepository;

@Service
@RequiredArgsConstructor
public class SizeQueryService {
    @NonNull
    final SizeRepository sizeRepository;
    @NonNull
    final SizeMapper sizeMapper;
//    public List<Size> findAll() {
//        List<SizeEntity> sizeEntityList = sizeRepository.findAll();
//        return sizeMapper.toDto(sizeEntityList);
//    }
    

    public Size findById(SizeId sizeId) {
        SizeEntity sizeEntity = sizeRepository.findById(sizeId.id()).orElseThrow(RuntimeException::new);
        return sizeMapper.toDto(sizeEntity);
    }

    public Page<Size> findAll(SizeQuery sizeQuery, PageRequest pageRequest) {
        Page<SizeEntity> sizeEntityPage = sizeRepository.customerFindAll(sizeQuery.search(), pageRequest);
        return sizeEntityPage.map(sizeMapper::toDto);
    }
}

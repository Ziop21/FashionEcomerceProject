package project.fashionecommerce.backend.fashionecommerceproject.service.database.size;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.fashionecommerce.backend.fashionecommerceproject.dto.size.Size;
import project.fashionecommerce.backend.fashionecommerceproject.dto.size.SizeId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.size.SizeMapper;
import project.fashionecommerce.backend.fashionecommerceproject.exception.MyConflictsException;
import project.fashionecommerce.backend.fashionecommerceproject.exception.MyResourceNotFoundException;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.size.SizeEntity;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.size.SizeRepository;

@Service
@RequiredArgsConstructor
public class SizeCommandService {
    @NonNull
    final SizeRepository sizeRepository;
    @NonNull
    final SizeMapper sizeMapper;

    public void save(Size size) {
        if (sizeRepository.existsByName(size.name()))
            throw new MyConflictsException();
        SizeEntity sizeEntity = sizeMapper.toEntity(size);
        sizeRepository.save(sizeEntity);
    }

    public void update(SizeId sizeId, Size size) {
        SizeEntity foundEntity = sizeRepository.findById(sizeId.id()).orElseThrow(MyResourceNotFoundException::new);
        if (!size.name().equals(foundEntity.getName())
                && sizeRepository.existsByName(size.name()))
            throw new MyConflictsException();
        sizeMapper.updateExisted(size, foundEntity);
        sizeRepository.save(foundEntity);
    }

    public void delete(SizeId sizeId) {
        sizeRepository.deleteById(sizeId.id());
    }
}

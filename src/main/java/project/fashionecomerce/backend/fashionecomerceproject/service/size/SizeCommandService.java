package project.fashionecomerce.backend.fashionecomerceproject.service.size;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.fashionecomerce.backend.fashionecomerceproject.dto.size.Size;
import project.fashionecomerce.backend.fashionecomerceproject.dto.size.SizeId;
import project.fashionecomerce.backend.fashionecomerceproject.dto.size.SizeMapper;
import project.fashionecomerce.backend.fashionecomerceproject.exception.MyConflictsException;
import project.fashionecomerce.backend.fashionecomerceproject.exception.MyResourceNotFoundException;
import project.fashionecomerce.backend.fashionecomerceproject.repository.size.SizeEntity;
import project.fashionecomerce.backend.fashionecomerceproject.repository.size.SizeRepository;

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
        sizeMapper.updateExist(size, foundEntity);
        sizeRepository.save(foundEntity);
    }

    public void delete(SizeId sizeId) {
        sizeRepository.deleteById(sizeId.id());
    }
}

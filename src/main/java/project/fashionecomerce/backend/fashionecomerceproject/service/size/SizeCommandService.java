package project.fashionecomerce.backend.fashionecomerceproject.service.size;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.fashionecomerce.backend.fashionecomerceproject.dto.size.Size;
import project.fashionecomerce.backend.fashionecomerceproject.dto.size.SizeId;
import project.fashionecomerce.backend.fashionecomerceproject.dto.size.SizeMapper;
import project.fashionecomerce.backend.fashionecomerceproject.exception.MyResourceNotFoundException;
import project.fashionecomerce.backend.fashionecomerceproject.repository.size.SizeEntity;
import project.fashionecomerce.backend.fashionecomerceproject.repository.size.SizeRepository;
import project.fashionecomerce.backend.fashionecomerceproject.service.generator.SequenceGeneratorService;

@Service
@RequiredArgsConstructor
public class SizeCommandService {
    @NonNull
    final SizeRepository sizeRepository;
    @NonNull
    final SizeMapper sizeMapper;
    @NonNull
    final SequenceGeneratorService sequenceGeneratorService;
    public void save(Size size) {
        SizeEntity sizeEntity = sizeMapper.toEntity(size);
        sizeEntity.setSequenceId(sequenceGeneratorService.generateSequence(sizeEntity.SEQUENCE_NAME));
        sizeRepository.save(sizeEntity);
    }

    public void update(SizeId sizeId, Size size) {
        SizeEntity foundEntity = sizeRepository.findById(sizeId.id()).orElseThrow(MyResourceNotFoundException::new);
        sizeMapper.updateExist(size, foundEntity);
        sizeRepository.save(foundEntity);
    }

    public void delete(SizeId sizeId) {
        sizeRepository.deleteById(sizeId.id());
    }
}

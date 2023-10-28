package project.fashionecommerce.backend.fashionecommerceproject.service.database.color;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.fashionecommerce.backend.fashionecommerceproject.dto.color.Color;
import project.fashionecommerce.backend.fashionecommerceproject.dto.color.ColorId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.color.ColorMapper;
import project.fashionecommerce.backend.fashionecommerceproject.exception.MyConflictsException;
import project.fashionecommerce.backend.fashionecommerceproject.exception.MyResourceNotFoundException;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.color.ColorEntity;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.color.ColorRepository;

@Service
@RequiredArgsConstructor
public class ColorCommandService {
    @NonNull
    final ColorRepository colorRepository;
    @NonNull
    final ColorMapper colorMapper;
    public void save(Color color) {
        if (colorRepository.existsByName(color.name()))
            throw new MyConflictsException();
        ColorEntity colorEntity = colorMapper.toEntity(color);
        colorRepository.save(colorEntity);
    }
    public void update(ColorId colorId, Color color) {
        ColorEntity colorEntity = colorRepository.findById(colorId.id()).orElseThrow(MyResourceNotFoundException::new);
        if(!color.name().equals(colorEntity.getName())&& colorRepository.existsByName(color.name()))
            throw new MyConflictsException();
        colorMapper.updateExisted(color, colorEntity);
        colorRepository.save(colorEntity);
    }
    public void delete(ColorId colorId) {
        colorRepository.deleteById(colorId.id());
    }
}

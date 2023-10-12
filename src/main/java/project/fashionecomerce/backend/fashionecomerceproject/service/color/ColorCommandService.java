package project.fashionecomerce.backend.fashionecomerceproject.service.color;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.fashionecomerce.backend.fashionecomerceproject.dto.color.Color;
import project.fashionecomerce.backend.fashionecomerceproject.dto.color.ColorId;
import project.fashionecomerce.backend.fashionecomerceproject.dto.color.ColorMapper;
import project.fashionecomerce.backend.fashionecomerceproject.exception.MyConflictsException;
import project.fashionecomerce.backend.fashionecomerceproject.exception.MyResourceNotFoundException;
import project.fashionecomerce.backend.fashionecomerceproject.repository.color.ColorEntity;
import project.fashionecomerce.backend.fashionecomerceproject.repository.color.ColorRepository;

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
        colorMapper.updateExist(color, colorEntity);
        colorRepository.save(colorEntity);
    }
    public void delete(ColorId colorId) {
        colorRepository.deleteById(colorId.id());
    }
}

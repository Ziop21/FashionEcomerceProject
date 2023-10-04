package project.fashionecomerce.backend.fashionecomerceproject.service.color;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import project.fashionecomerce.backend.fashionecomerceproject.controller.common.PageResponse;
import project.fashionecomerce.backend.fashionecomerceproject.dto.color.Color;
import project.fashionecomerce.backend.fashionecomerceproject.dto.color.ColorId;
import project.fashionecomerce.backend.fashionecomerceproject.dto.color.ColorMapper;
import project.fashionecomerce.backend.fashionecomerceproject.dto.color.ColorQuery;
import project.fashionecomerce.backend.fashionecomerceproject.repository.color.ColorEntity;
import project.fashionecomerce.backend.fashionecomerceproject.repository.color.ColorRepository;

@Service
@RequiredArgsConstructor
public class ColorQueryService {
    @NonNull
    final ColorRepository colorRepository;
    @NonNull
    final ColorMapper colorMapper;
    public Page<Color> findAll(ColorQuery colorQuery, PageRequest pageRequest) {
        Page<ColorEntity> colorEntityPage = colorRepository.customerFindAll(colorQuery.search(),pageRequest);
        return colorEntityPage.map(colorMapper::toDto);
    }

    public Color findById(ColorId colorId) {
        ColorEntity colorEntity = colorRepository.findById(colorId.id()).orElseThrow(RuntimeException::new);
        return colorMapper.toDto(colorEntity);
    }
}

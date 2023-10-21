package project.fashionecommerce.backend.fashionecommerceproject.service.database.color;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import project.fashionecommerce.backend.fashionecommerceproject.dto.color.Color;
import project.fashionecommerce.backend.fashionecommerceproject.dto.color.ColorId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.color.ColorMapper;
import project.fashionecommerce.backend.fashionecommerceproject.dto.color.ColorQuery;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.color.ColorEntity;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.color.ColorRepository;

@Service
@RequiredArgsConstructor
public class ColorQueryService {
    @NonNull
    final ColorRepository colorRepository;
    @NonNull
    final ColorMapper colorMapper;
    public Page<Color> findAll(ColorQuery colorQuery, PageRequest pageRequest) {
        Page<ColorEntity> colorEntityPage = colorRepository.customerFindAll(colorQuery.search(), pageRequest);
        return colorEntityPage.map(colorMapper::toDto);
    }
    public Color findById(ColorId colorId) {
        ColorEntity colorEntity = colorRepository.findById(colorId.id()).orElseThrow(RuntimeException::new);
        return colorMapper.toDto(colorEntity);
    }
}

package project.fashionecommerce.backend.fashionecommerceproject.service.database.color;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.fashionecommerce.backend.fashionecommerceproject.dto.color.Color;
import project.fashionecommerce.backend.fashionecommerceproject.dto.color.ColorId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.color.ColorQuery;

@Service
@RequiredArgsConstructor
public class ColorUseCaseService {
    @NonNull
    final ColorCommandService colorCommandService;
    @NonNull
    final ColorQueryService colorQueryService;

    @Transactional
    public void save(Color color) {
        colorCommandService.save(color);
    }
    @Transactional
    public void update(ColorId colorId, Color color) {
        colorCommandService.update(colorId, color);
    }
    @Transactional
    public void delete(ColorId colorId) {
        colorCommandService.delete(colorId);
    }
    public Page<Color> findAll(ColorQuery colorQuery, PageRequest pageRequest) {
        return colorQueryService.findAll(colorQuery, pageRequest);
    }

    public Color findById(ColorId colorId) {
        return colorQueryService.findById(colorId);
    }
}
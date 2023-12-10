package project.fashionecommerce.backend.fashionecommerceproject.service.guest;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.fashionecommerce.backend.fashionecommerceproject.dto.color.Color;
import project.fashionecommerce.backend.fashionecommerceproject.dto.color.ColorId;
import project.fashionecommerce.backend.fashionecommerceproject.exception.MyResourceNotFoundException;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.color.ColorQueryService;

@Service
@RequiredArgsConstructor
public class GuestColorUseCaseService {
    @NonNull
    final ColorQueryService colorQueryService;
    public Color findById(ColorId colorId) {
        Color color = colorQueryService.findById(colorId);
        if (color.isDeleted() || !color.isActive())
            throw new MyResourceNotFoundException();
        return color;
    }
}

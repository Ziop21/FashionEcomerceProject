package project.fashionecommerce.backend.fashionecommerceproject.service.guest;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.fashionecommerce.backend.fashionecommerceproject.dto.size.Size;
import project.fashionecommerce.backend.fashionecommerceproject.dto.size.SizeId;
import project.fashionecommerce.backend.fashionecommerceproject.exception.MyResourceNotFoundException;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.size.SizeQueryService;

@Service
@RequiredArgsConstructor
public class GuestSizeUseCaseService {
    @NonNull final SizeQueryService sizeQueryService;
    public Size findById(SizeId sizeId) {
        Size size = sizeQueryService.findById(sizeId);
        if (!size.isActive() || size.isDeleted())
            throw new MyResourceNotFoundException();
        return size;
    }
}

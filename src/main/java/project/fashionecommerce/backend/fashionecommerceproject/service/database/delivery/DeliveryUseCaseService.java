package project.fashionecommerce.backend.fashionecommerceproject.service.database.delivery;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.fashionecommerce.backend.fashionecommerceproject.dto.delivery.Delivery;
import project.fashionecommerce.backend.fashionecommerceproject.dto.delivery.DeliveryId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.delivery.DeliveryQuery;

@Service
@RequiredArgsConstructor
public class DeliveryUseCaseService {
    @NonNull final DeliveryCommandService deliveryCommandService;
    @NonNull final DeliveryQueryService deliveryQueryService;
    @Transactional
    public void update(DeliveryId deliveryId, Delivery delivery) {
        deliveryCommandService.update(deliveryId, delivery);
    }
    @Transactional
    public void delete(DeliveryId deliveryId) {
       deliveryCommandService.delete(deliveryId);
    }
    @Transactional
    public void save(Delivery delivery) {
        deliveryCommandService.save(delivery);
    }

    public Page<Delivery> findAll(DeliveryQuery deliveryQuery, PageRequest pageRequest) {
        return deliveryQueryService.findAll(deliveryQuery, pageRequest);
    }

    public Delivery findById(DeliveryId deliveryId) {
        return deliveryQueryService.findById(deliveryId);
    }

}

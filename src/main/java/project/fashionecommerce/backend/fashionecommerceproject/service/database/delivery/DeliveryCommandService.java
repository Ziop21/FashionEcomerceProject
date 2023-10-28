package project.fashionecommerce.backend.fashionecommerceproject.service.database.delivery;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.fashionecommerce.backend.fashionecommerceproject.dto.delivery.Delivery;
import project.fashionecommerce.backend.fashionecommerceproject.dto.delivery.DeliveryId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.delivery.DeliveryMapper;
import project.fashionecommerce.backend.fashionecommerceproject.exception.MyConflictsException;
import project.fashionecommerce.backend.fashionecommerceproject.exception.MyResourceNotFoundException;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.delivery.DeliveryEntity;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.delivery.DeliveryRepository;

@Service
@RequiredArgsConstructor
public class DeliveryCommandService {
    @NonNull final DeliveryRepository deliveryRepository;
    @NonNull final DeliveryMapper deliveryMapper;

    public void save(Delivery delivery) {
        if (deliveryRepository.existsByName(delivery.name()))
            throw new MyConflictsException();
        DeliveryEntity deliveryEntity = deliveryMapper.toEntity(delivery);
        deliveryRepository.save(deliveryEntity);
    }

    public void delete(DeliveryId deliveryId) {
        deliveryRepository.deleteById(deliveryId.id());
    }

    public void update(DeliveryId deliveryId, Delivery delivery) {
        DeliveryEntity foundEntity = deliveryRepository.findById(deliveryId.id())
                .orElseThrow(MyResourceNotFoundException::new);
        if (!foundEntity.getName().equals(delivery.name())
                && deliveryRepository.existsByName(delivery.name()))
            throw new MyConflictsException();
        deliveryMapper.updateExisted(delivery, foundEntity);
        deliveryRepository.save(foundEntity);
    }
}

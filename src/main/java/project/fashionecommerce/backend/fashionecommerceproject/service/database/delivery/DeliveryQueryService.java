package project.fashionecommerce.backend.fashionecommerceproject.service.database.delivery;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import project.fashionecommerce.backend.fashionecommerceproject.dto.delivery.Delivery;
import project.fashionecommerce.backend.fashionecommerceproject.dto.delivery.DeliveryId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.delivery.DeliveryMapper;
import project.fashionecommerce.backend.fashionecommerceproject.dto.delivery.DeliveryQuery;
import project.fashionecommerce.backend.fashionecommerceproject.exception.MyResourceNotFoundException;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.delivery.DeliveryEntity;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.delivery.DeliveryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryQueryService {
    @NonNull final DeliveryRepository deliveryRepository;
    @NonNull final DeliveryMapper deliveryMapper;
    public Page<Delivery> findAll(DeliveryQuery deliveryQuery, PageRequest pageRequest) {
        Page<DeliveryEntity> deliveryEntities = deliveryRepository.customFindAll(deliveryQuery.search(), pageRequest);
        return deliveryEntities.map(deliveryMapper::toDto);
    }

    public Delivery findById(DeliveryId deliveryId) {
        DeliveryEntity deliveryEntity = deliveryRepository.findById(deliveryId.id())
                .orElseThrow(MyResourceNotFoundException::new);
        return deliveryMapper.toDto(deliveryEntity);
    }
}

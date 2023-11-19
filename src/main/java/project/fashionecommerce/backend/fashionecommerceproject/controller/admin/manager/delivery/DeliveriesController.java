package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.delivery;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import project.fashionecommerce.backend.fashionecommerceproject.common.models.PageResponse;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.delivery.models.DeliveryModelMapper;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.delivery.models.DeliveryRequest;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.manager.delivery.models.DeliveryResponse;
import project.fashionecommerce.backend.fashionecommerceproject.dto.delivery.Delivery;
import project.fashionecommerce.backend.fashionecommerceproject.dto.delivery.DeliveryQuery;
import project.fashionecommerce.backend.fashionecommerceproject.dto.handler.MySortHandler;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.delivery.DeliveryUseCaseService;

@RestController
@RequiredArgsConstructor
public class DeliveriesController implements DeliveriesAPI{
    @NonNull final DeliveryUseCaseService deliveryUseCaseService;
    @NonNull final DeliveryModelMapper deliveryModelMapper;
    @Override
    public void save(DeliveryRequest deliveryRequest) {
        Delivery delivery = deliveryModelMapper.toDto(deliveryRequest);
        deliveryUseCaseService.save(delivery);
    }

    @Override
    public ResponseEntity<PageResponse<DeliveryResponse>> findAll(String search, String sort, Integer currentPage, Integer pageSize) {
        DeliveryQuery deliveryQuery = new DeliveryQuery(search);

        PageRequest pageRequest = PageRequest.of(currentPage-1, pageSize, MySortHandler.of(sort));

        Page<Delivery> deliveryPage = deliveryUseCaseService.findAll(deliveryQuery, pageRequest);

        PageResponse<DeliveryResponse> deliveryResponsePage = new PageResponse<>(deliveryPage.map(deliveryModelMapper::toModel));
        return new ResponseEntity<>(deliveryResponsePage, HttpStatus.OK);
    }
}

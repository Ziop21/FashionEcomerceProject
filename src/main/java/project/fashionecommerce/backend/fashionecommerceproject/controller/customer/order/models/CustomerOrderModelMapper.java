package project.fashionecommerce.backend.fashionecommerceproject.controller.customer.order.models;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import project.fashionecommerce.backend.fashionecommerceproject.controller.customer.order.items.models.CustomerOrderItemResponse;
import project.fashionecommerce.backend.fashionecommerceproject.dto.order.Order;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerOrderModelMapper {
    CustomerOrderResponse toModel(Order order);
    @Mapping(source = "id", target = "productId")
    @Mapping(source = "name", target = "productName")
    @Mapping(source = "price", target = "price")
    CustomerOrderItemResponse updateModelProduct(CustomerOrderItemResponse orderItem, String id, String name, Double price);

    @Mapping(source = "id", target = "sizeId")
    @Mapping(source = "name", target = "sizeName")
    CustomerOrderItemResponse updateModelSize(CustomerOrderItemResponse orderItem, String id, String name);

    @Mapping(source = "id", target = "colorId")
    @Mapping(source = "name", target = "colorName")
    CustomerOrderItemResponse updateModelColor(CustomerOrderItemResponse orderItem, String id, String name);


    @Mapping(source = "orderItemResponses", target = "orderItems")
    CustomerOrderResponse updateModelOrderItems(Order order, List<CustomerOrderItemResponse> orderItemResponses);
}

package project.fashionecommerce.backend.fashionecommerceproject.controller.customer.follow;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import project.fashionecommerce.backend.fashionecommerceproject.controller.customer.follow.models.CustomerFollowModelMapper;
import project.fashionecommerce.backend.fashionecommerceproject.controller.customer.follow.models.CustomerFollowResponse;
import project.fashionecommerce.backend.fashionecommerceproject.dto.follow.Follow;
import project.fashionecommerce.backend.fashionecommerceproject.dto.follow.FollowId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.follow.FollowQuery;
import project.fashionecommerce.backend.fashionecommerceproject.dto.product.ProductId;
import project.fashionecommerce.backend.fashionecommerceproject.service.customer.CustomerFollowUseCaseService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class CustomerFollowController implements CustomerFollowAPI {
    @NonNull final CustomerFollowUseCaseService customerFollowUseCaseService;
    @NonNull final CustomerFollowModelMapper customerFollowModelMapper;
    @Override
    public void save(String productId) {
        customerFollowUseCaseService.save(new ProductId(productId));
    }

    @Override
    public ResponseEntity<List<CustomerFollowResponse>> findAll(String search) {
        FollowQuery followQuery = FollowQuery.builder().search(search).build();
        List<Follow> follows = customerFollowUseCaseService.findAll(followQuery);
       return new ResponseEntity<>(follows.stream().map(customerFollowModelMapper::toModel).collect(Collectors.toList()),
               HttpStatus.OK);
    }

    @Override
    public void delete(String followId) {
        customerFollowUseCaseService.delete(new FollowId(followId));
    }
}

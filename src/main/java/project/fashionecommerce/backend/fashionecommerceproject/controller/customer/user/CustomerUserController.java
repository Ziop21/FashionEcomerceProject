package project.fashionecommerce.backend.fashionecommerceproject.controller.customer.user;

import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import project.fashionecommerce.backend.fashionecommerceproject.controller.customer.user.models.CustomerPasswordRequest;
import project.fashionecommerce.backend.fashionecommerceproject.controller.customer.user.models.CustomerUserModelMapper;
import project.fashionecommerce.backend.fashionecommerceproject.controller.customer.user.models.CustomerUserRequest;
import project.fashionecommerce.backend.fashionecommerceproject.controller.customer.user.models.CustomerUserResponse;
import project.fashionecommerce.backend.fashionecommerceproject.dto.user.User;
import project.fashionecommerce.backend.fashionecommerceproject.service.customer.CustomerUserUseCaseService;

@RestController
@RequiredArgsConstructor
public class CustomerUserController implements CustomerUserAPI{
    @NonNull final CustomerUserUseCaseService customerUserUseCaseService;
    @NonNull final CustomerUserModelMapper customerUserModelMapper;
    @Override
    public ResponseEntity<CustomerUserResponse> findById() {
        User user = customerUserUseCaseService.findById();
        return new ResponseEntity<>(customerUserModelMapper.toModel(user), HttpStatus.OK);
    }

    @Override
    public void update(CustomerUserRequest customerUserRequest) {
        User user = customerUserModelMapper.toDto(customerUserRequest);
        customerUserUseCaseService.update(user);
    }

    @Override
    public void updatePassword(CustomerPasswordRequest customerPasswordRequest) {
        customerUserUseCaseService.updatePassword(customerPasswordRequest);
    }

    @Override
    public void delete(CustomerPasswordRequest customerPasswordRequest) {
        customerUserUseCaseService.delete(customerPasswordRequest.confirmPassword());
    }
}

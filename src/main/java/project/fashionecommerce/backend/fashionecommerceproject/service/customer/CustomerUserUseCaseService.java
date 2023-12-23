package project.fashionecommerce.backend.fashionecommerceproject.service.customer;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.fashionecommerce.backend.fashionecommerceproject.config.security.userDetails.Implement.UserDetailsImpl;
import project.fashionecommerce.backend.fashionecommerceproject.controller.customer.user.models.CustomerPasswordRequest;
import project.fashionecommerce.backend.fashionecommerceproject.dto.user.User;
import project.fashionecommerce.backend.fashionecommerceproject.dto.user.UserId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.user.UserMapper;
import project.fashionecommerce.backend.fashionecommerceproject.dto.user.level.UserLevel;
import project.fashionecommerce.backend.fashionecommerceproject.dto.user.level.UserLevelId;
import project.fashionecommerce.backend.fashionecommerceproject.exception.MyConfirmPasswordUnmatchException;
import project.fashionecommerce.backend.fashionecommerceproject.exception.MyResourceNotFoundException;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.token.TokenCommandService;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.user.UserCommandService;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.user.UserQueryService;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.user.level.UserLevelQueryService;

@Service
@RequiredArgsConstructor
public class CustomerUserUseCaseService {
    @NonNull final UserCommandService userCommandService;

    @NonNull final UserQueryService userQueryService;
    @NonNull final UserLevelQueryService userLevelQueryService;
    @NonNull final TokenCommandService tokenCommandService;

    @NonNull final UserMapper userMapper;

    @Autowired
    final PasswordEncoder passwordEncoder;

    public User findById() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userQueryService.findById(new UserId(userDetails.getId()));
        if (user.isDeleted() || !user.isActive()){
            throw new MyResourceNotFoundException();
        }
        if (user.userLevelId() != null){
            UserLevel userLevel = userLevelQueryService.findById(new UserLevelId(user.userLevelId()));
            user = userMapper.updateDtoUserLevelName(user, userLevel.name());
        }
        return user;
    }
    @Transactional
    public void update(User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User foundUser = userQueryService.findById(new UserId(userDetails.getId()));
        if (!foundUser.isActive() || foundUser.isDeleted()){
            throw new MyResourceNotFoundException();
        }
        if (!passwordEncoder.matches(user.hashedPassword(), userDetails.getPassword())){
            throw new MyConfirmPasswordUnmatchException();
        }
        foundUser = userMapper.updateDto(foundUser, user.firstName(), user.lastName(),
                user.idCard(), user.phones(), user.addresses(), user.avatar(), user.eWallet());
        userCommandService.update(new UserId(foundUser.id()), foundUser);
    }
    @Transactional
    public void delete(String password) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User foundUser = userQueryService.findById(new UserId(userDetails.getId()));
        if (!passwordEncoder.matches(password, userDetails.getPassword())){
            throw new MyConfirmPasswordUnmatchException();
        }
        foundUser = userMapper.updateDtoIsActive(foundUser, false);
        foundUser = userMapper.updateDtoIsDeleted(foundUser, true);
        tokenCommandService.deleteByUserId(foundUser.id());
        userCommandService.update(new UserId(userDetails.getId()), foundUser);
    }
    @Transactional
    public void updatePassword(CustomerPasswordRequest customerPasswordRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        if (!passwordEncoder.matches(customerPasswordRequest.oldPassword(), userDetails.getPassword())){
            throw new MyConfirmPasswordUnmatchException();
        }
        if (!customerPasswordRequest.newPassword().equals(customerPasswordRequest.confirmPassword())){
            throw new MyConfirmPasswordUnmatchException();
        }
        userCommandService.updateHashedPasswordAndIsActive(
                new UserId(userDetails.getId()),
                passwordEncoder.encode(customerPasswordRequest.newPassword()),
                true);
    }
}

package project.fashionecomerce.backend.fashionecomerceproject.service.register;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import project.fashionecomerce.backend.fashionecomerceproject.dto.user.UserMapper;
import project.fashionecomerce.backend.fashionecomerceproject.dto.verification.token.VerificationToken;
import project.fashionecomerce.backend.fashionecomerceproject.dto.user.User;
import project.fashionecomerce.backend.fashionecomerceproject.exception.MyConflictsException;
import project.fashionecomerce.backend.fashionecomerceproject.exception.MyInvalidTokenException;
import project.fashionecomerce.backend.fashionecomerceproject.repository.verification.token.VerificationTokenEntity;
import project.fashionecomerce.backend.fashionecomerceproject.service.user.UserCommandService;
import project.fashionecomerce.backend.fashionecomerceproject.service.user.UserQueryService;
import org.springframework.mail.javamail.JavaMailSender;
import project.fashionecomerce.backend.fashionecomerceproject.service.verification.token.VerificationTokenCommandService;
import project.fashionecomerce.backend.fashionecomerceproject.service.verification.token.VerificationTokenQueryService;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RegisterUseCaseService {
    @NonNull final UserCommandService userCommandService;
    @NonNull final UserQueryService userQueryService;
    @NonNull final VerificationTokenCommandService verificationTokenCommandService;
    @NonNull final VerificationTokenQueryService verificationTokenQueryService;
    @NonNull final UserMapper userMapper;

    public void sendToken(String email) {
        if (userQueryService.existsByEmail(email))
            throw new MyConflictsException();

        User user = User.builder()
                .email(email)
                .isEmailActive(false)
                .isDeleted(false)
                .build();
        user = userCommandService.save(user);

        VerificationToken token = VerificationToken.builder()
                .userId(user.id())
                .build();

        token = verificationTokenCommandService.save(token);
        sendEmailVerification(email, token);
    }
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmailVerification(String email, VerificationToken token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Xác nhận địa chỉ email");
        message.setText("Nhấn vào đường link sau để xác nhận email: "
                + "http://localhost:8081/register/verify?token=" + token.token() + "&email=" + email);
        javaMailSender.send(message);
    }

    public String verifyToken(String token, String email) {
        User user = userQueryService.findByEmail(email);
        VerificationToken foundToken = verificationTokenQueryService.findByUserId(user.id());
        if (!token.equals(foundToken.token())
        || foundToken.expiryDateTime().isBefore(LocalDateTime.now()))
            throw new MyInvalidTokenException();
        user = userMapper.updateDtoIsEmailActive(user, true);
        userCommandService.save(user);
        return email;
    }
}

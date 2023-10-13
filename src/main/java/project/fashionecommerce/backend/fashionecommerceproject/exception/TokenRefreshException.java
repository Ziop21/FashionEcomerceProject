package project.fashionecommerce.backend.fashionecommerceproject.exception;

import org.springframework.http.HttpStatus;

public class TokenRefreshException extends ApplicationException {

    public TokenRefreshException(String token, String message) {
        super("TOKEN_101", String.format("Failed for [%s]: %s", token, message), HttpStatus.FORBIDDEN);
    }
}
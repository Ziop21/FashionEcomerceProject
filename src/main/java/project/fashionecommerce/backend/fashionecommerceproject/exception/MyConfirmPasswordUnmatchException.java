package project.fashionecommerce.backend.fashionecommerceproject.exception;

import org.springframework.http.HttpStatus;

public class MyConfirmPasswordUnmatchException extends ApplicationException {
    public MyConfirmPasswordUnmatchException(){
        super(null, "Confirm pasword unmatched...", HttpStatus.UNAUTHORIZED);
    }
}
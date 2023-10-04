package project.fashionecomerce.backend.fashionecomerceproject.exception;

import org.springframework.http.HttpStatus;

public class MyInvalidTokenException extends ApplicationException{
    public MyInvalidTokenException(){
        super("SYS_501", "invalid token...", HttpStatus.NOT_ACCEPTABLE);
    }
}

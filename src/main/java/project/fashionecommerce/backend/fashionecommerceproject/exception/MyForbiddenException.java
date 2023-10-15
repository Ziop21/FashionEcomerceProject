package project.fashionecommerce.backend.fashionecommerceproject.exception;

import org.springframework.http.HttpStatus;

public class MyForbiddenException extends ApplicationException{
    public MyForbiddenException(){
        super("SYS_301", "forbidden...", HttpStatus.FORBIDDEN);
    }
}

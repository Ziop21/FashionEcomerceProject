package project.fashionecommerce.backend.fashionecommerceproject.exception;

import org.springframework.http.HttpStatus;

public class MyNeedWaitingException extends ApplicationException{
    public MyNeedWaitingException(){
        super("SYS_301", "Need waiting", HttpStatus.FORBIDDEN);
    }
}

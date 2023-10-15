package project.fashionecommerce.backend.fashionecommerceproject.exception;

import org.springframework.http.HttpStatus;
public class MyResourceNotFoundException extends ApplicationException{
    public MyResourceNotFoundException(){
        super("SYS_404", "resource not found...", HttpStatus.NOT_FOUND);
    }
}

package project.fashionecommerce.backend.fashionecommerceproject.exception;

import org.springframework.http.HttpStatus;

public class OutOfQuantityException extends ApplicationException{
    public OutOfQuantityException(){
        super("SYS_301", "Out of quantity", HttpStatus.NOT_FOUND);
    }
}

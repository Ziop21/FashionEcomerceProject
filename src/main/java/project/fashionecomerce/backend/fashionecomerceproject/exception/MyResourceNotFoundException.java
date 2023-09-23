package project.fashionecomerce.backend.fashionecomerceproject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class MyResourceNotFoundException extends ApplicationException{
    public MyResourceNotFoundException(){
        super("SYS_404", "resource not found...", HttpStatus.NOT_FOUND);
    }
}

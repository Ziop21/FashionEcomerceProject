package project.fashionecomerce.backend.fashionecomerceproject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class MyConflictsException extends ApplicationException{
    public MyConflictsException(){
        super("SYS_301", "data conflict...", HttpStatus.CONFLICT);
    }
}

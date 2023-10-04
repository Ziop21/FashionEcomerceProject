package project.fashionecomerce.backend.fashionecomerceproject.exception;

import org.springframework.http.HttpStatus;
public class MyConflictsException extends ApplicationException{
    public MyConflictsException(){
        super("SYS_301", "data conflict...", HttpStatus.CONFLICT);
    }
}

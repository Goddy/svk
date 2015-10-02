package be.spring.app.controller.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by u0090265 on 8/30/14.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ObjectNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public ObjectNotFoundException(String message) {
        super(message);
    }
}

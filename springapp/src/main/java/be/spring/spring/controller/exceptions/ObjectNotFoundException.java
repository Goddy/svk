package be.spring.spring.controller.exceptions;

/**
 * Created by u0090265 on 8/30/14.
 */
public class ObjectNotFoundException extends RuntimeException {
    public ObjectNotFoundException(String message) {
        super(message);
    }
}

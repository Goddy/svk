package be.spring.app.controller.exceptions;

/**
 * Created by u0090265 on 8/30/14.
 */
public class ObjectNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public ObjectNotFoundException(String message) {
        super(message);
    }
}

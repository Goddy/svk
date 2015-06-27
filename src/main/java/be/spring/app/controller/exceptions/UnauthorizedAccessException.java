package be.spring.app.controller.exceptions;

/**
 * Created by u0090265 on 9/19/14.
 */
public class UnauthorizedAccessException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public UnauthorizedAccessException(String message) {
        super(message);
    }
}

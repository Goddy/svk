package be.svk.webapp.controller;

import be.svk.webapp.dto.ErrorDetailDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.social.ResourceNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by u0090265 on 2/19/16.
 */
@ControllerAdvice(annotations = org.springframework.web.bind.annotation.RestController.class)
public class RestExceptionHandler {

    static final String TITLE_RESOURCE_NOT_FOUND = "Could not find resource";
    static final String TITLE_ACCESS_DENIED_ERROR = "Access denied";
    private static Logger log = LoggerFactory.getLogger(RestExceptionHandler.class);

    public RestExceptionHandler() {
    }

    @ExceptionHandler
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException e, HttpServletRequest request) {
        ErrorDetailDTO errorDetail = createErrorDetail(new ErrorDetailDTO(),
                HttpStatus.NOT_FOUND.value(),
                TITLE_RESOURCE_NOT_FOUND,
                request,
                e);
        log.warn("handleResourceNotFoundException - Resource not found, returning http status {}", HttpStatus
                .NOT_FOUND.name());
        return new ResponseEntity<>(errorDetail, null, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException e, HttpServletRequest request) {
        ErrorDetailDTO errorDetail = createErrorDetail(new ErrorDetailDTO(),
                HttpStatus.FORBIDDEN.value(),
                TITLE_ACCESS_DENIED_ERROR,
                request,
                e);
        log.warn("handleAccessDeniedException - Access denied, returning http status {}", HttpStatus.FORBIDDEN.name());
        return new ResponseEntity<>(errorDetail, null, HttpStatus.FORBIDDEN);
    }

    private String getRequestURI(HttpServletRequest request) {
        return request.getRequestURI();
    }

    private <T extends ErrorDetailDTO> T createErrorDetail(T errorDetail, int status, String title,
                                                           HttpServletRequest request, Exception e) {
        errorDetail.setStatus(status);
        errorDetail.setTitle(title);
        errorDetail.setPath(getRequestURI(request));
        errorDetail.setDetail(e.getMessage());
        errorDetail.setDeveloperMessage(e.getClass().getName());
        return errorDetail;
    }
}

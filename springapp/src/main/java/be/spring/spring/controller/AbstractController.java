package be.spring.spring.controller;

import be.spring.spring.model.Account;
import be.spring.spring.utils.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import java.util.Locale;

/**
 * User: Tom De Dobbeleer
 * Date: 1/20/14
 * Time: 1:43 PM
 * Remarks: none
 */
public abstract class AbstractController {

    @Autowired
    private MessageSource messageSource;

    private static final Logger log = LoggerFactory.getLogger(AbstractController.class);

    public Account getAccountFromSecurity() {
        return SecurityUtils.getAccountFromSecurity();
    }

    /**
     * Get a message from the message source
     * @param msg   The message code
     * @param args  The arguments for the message, null if empty
     * @param locale    The locale
     * @return
     */
    protected String getMessage(String msg, Object [] args, Locale locale) {
        try {
            return messageSource.getMessage(msg, args, locale);
        }

        catch (NoSuchMessageException e) {
            log.debug("messageSourceError - {}", e.getMessage());
            return null;
        }
    }
}

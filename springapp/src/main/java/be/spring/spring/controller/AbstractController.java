package be.spring.spring.controller;

import be.spring.spring.model.Account;
import be.spring.spring.utils.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Locale;

/**
 * User: Tom De Dobbeleer
 * Date: 1/20/14
 * Time: 1:43 PM
 * Remarks: none
 */
public abstract class AbstractController {

    private static final String DIV_CLASS_SUCCESS = "alert alert-success";
    @Autowired
    private MessageSource messageSource;

    @Autowired
    private SecurityUtils securityUtils;

    private static final Logger log = LoggerFactory.getLogger(AbstractController.class);

    public Account getAccountFromSecurity() {
        return securityUtils.getAccountFromSecurity();
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

    public void setSuccessMessage(ModelMap model, Locale locale, String code, Object[] args) {
        try {
            model.addAttribute("resultMessage", messageSource.getMessage(code, args, locale));
            model.addAttribute("divClass", DIV_CLASS_SUCCESS);
        } catch (NoSuchMessageException e) {
            log.debug("messageSourceError for success message - {}", e.getMessage());
        }
    }

    public void setSuccessMessage(RedirectAttributes redirectAttributes, Locale locale, String code, Object[] args) {
        try {
            redirectAttributes.addAttribute("resultMessage", messageSource.getMessage(code, args, locale));
            redirectAttributes.addAttribute("divClass", DIV_CLASS_SUCCESS);
        } catch (NoSuchMessageException e) {
            log.debug("messageSourceError for success message - {}", e.getMessage());
        }
    }
}

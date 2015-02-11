package be.spring.app.controller;

import be.spring.app.controller.exceptions.ObjectNotFoundException;
import be.spring.app.controller.exceptions.UnauthorizedAccessException;
import be.spring.app.model.Account;
import be.spring.app.service.CatchPaService;
import be.spring.app.service.MailService;
import be.spring.app.utils.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * User: Tom De Dobbeleer
 * Date: 1/20/14
 * Time: 1:43 PM
 * Remarks: none
 */
public abstract class AbstractController {

    private static final String DIV_CLASS_SUCCESS = "alert alert-success";
    private static final String DIV_CLASS_ERROR = "alert alert-danger";
    @Autowired
    protected MailService mailService;

    @Autowired
    protected CatchPaService catchPaService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private SecurityUtils securityUtils;

    @ExceptionHandler(ObjectNotFoundException.class)
    public String handleException(Exception e, HttpServletRequest request) {
        log.error(e.getMessage());
        request.setAttribute("message", "error.object.unknown");
        return "error";
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public String handleUnauthorizedException(Exception e) {
        log.error(e.getMessage());
        return "error-403";
    }

    /**
     * Blocks spring security exceptions
     * @param e
     * @return
    @ExceptionHandler(RuntimeException.class)
    public String handleRuntimeException(Exception e) {
        log.error(e.getMessage());
        return "error-500";
    }
     **/

    private static final Logger log = LoggerFactory.getLogger(AbstractController.class);

    public Account getAccountFromSecurity() {
        return securityUtils.getAccountFromSecurity();
    }


    public boolean isLoggedIn() {
        return securityUtils.isloggedIn();
    }

    /**
     * Get a message from the message source
     *
     * @param msg    The message code
     * @param args   The arguments for the message, null if empty
     * @param locale The locale
     * @return
     */
    protected String getMessage(String msg, Object[] args, Locale locale) {
        try {
            return messageSource.getMessage(msg, args, locale);
        } catch (NoSuchMessageException e) {
            log.debug("messageSourceError - {}", e.getMessage());
            return null;
        }
    }

    public void setFlashSuccessMessage(RedirectAttributes redirectAttributes, Locale locale, String code, Object[] args) {
        try {
            redirectAttributes.addFlashAttribute("resultMessage", messageSource.getMessage(code, args, locale));
            redirectAttributes.addFlashAttribute("divClass", DIV_CLASS_SUCCESS);
        } catch (NoSuchMessageException e) {
            log.debug("messageSourceError for success message - {}", e.getMessage());
        }
    }

    public <T extends Model> void setSuccessMessage(T model, Locale locale, String code, Object[] args) {
        try {
            model.addAttribute("resultMessage", messageSource.getMessage(code, args, locale));
            model.addAttribute("divClass", DIV_CLASS_SUCCESS);
        } catch (NoSuchMessageException e) {
            log.debug("messageSourceError for success message - {}", e.getMessage());
        }
    }

    public <T extends Model> void setErrorMessage(T model, Locale locale, String code, Object[] args) {
        try {
            model.addAttribute("resultMessage", messageSource.getMessage(code, args, locale));
            model.addAttribute("divClass", DIV_CLASS_ERROR);
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

    public String getRedirect(String landing) {
        return "redirect:" + landing + ".html";
    }

    public String getDefaultMessages(BindingResult result) {
        StringBuilder builder = new StringBuilder();
        for (FieldError r : result.getFieldErrors()) {
            builder.append(r.getDefaultMessage())
                    .append("</br>");
        }
        return builder.toString();
    }

    protected void populateRecatchPa(Model model, boolean b) {
        model.addAttribute("privateKey", catchPaService.getPrivateKey());
        model.addAttribute("publicKey", catchPaService.getPublicKey());
        model.addAttribute("invalidRecaptcha", !b);
    }
}

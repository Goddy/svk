package be.svk.webapp.controller;

import be.svk.webapp.controller.exceptions.ObjectNotFoundException;
import be.svk.webapp.controller.exceptions.UnauthorizedAccessException;
import be.svk.webapp.service.CaptchaService;
import be.svk.webapp.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Locale;

/**
 * User: Tom De Dobbeleer
 * Date: 1/20/14
 * Time: 1:43 PM
 * Remarks: none
 */
public abstract class AbstractController extends AbstractSecurityController {

    private static final String DIV_CLASS_SUCCESS = "alert alert-success";
    private static final String DIV_CLASS_ERROR = "alert alert-danger";
    /**
     * Blocks spring security exceptions
     *
     * @param e
     * @return
     * @ExceptionHandler(RuntimeException.class) public String handleRuntimeException(Exception e) {
     * log.error(e.getMessage());
     * return "error-500";
     * }
     **/

    private static final Logger log = LoggerFactory.getLogger(AbstractController.class);
    @Autowired
    protected MailService mailService;
    @Autowired
    protected CaptchaService captchaService;
    @Autowired
    private MessageSource messageSource;

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

    public <T extends Model> void setSuccessMessage(T model, String text) {
        model.addAttribute("resultMessage", text);
        model.addAttribute("divClass", DIV_CLASS_SUCCESS);
    }

    public <T extends Model> void setErrorMessage(T model, Locale locale, String code, Object[] args) {
        try {
            model.addAttribute("resultMessage", messageSource.getMessage(code, args, locale));
            model.addAttribute("divClass", DIV_CLASS_ERROR);
        } catch (NoSuchMessageException e) {
            log.debug("messageSourceError for success message - {}", e.getMessage());
        }
    }

    public <T extends Model> void setErrorMessage(T model,String text) {
        model.addAttribute("resultMessage", text);
        model.addAttribute("divClass", DIV_CLASS_ERROR);
    }

    public void setSuccessMessage(RedirectAttributes redirectAttributes, Locale locale, String code, Object[] args) {
        try {
            redirectAttributes.addAttribute("resultMessage", messageSource.getMessage(code, args, locale));
            redirectAttributes.addAttribute("divClass", DIV_CLASS_SUCCESS);
        } catch (NoSuchMessageException e) {
            log.debug("messageSourceError for success message - {}", e.getMessage());
        }
    }

    public String getDefaultMessages(BindingResult result, Locale locale) {
        StringBuilder builder = new StringBuilder();
        for (ObjectError r : result.getAllErrors()) {
            builder.append(getMessage(r.getDefaultMessage(), null, locale))
                    .append("</br>");
        }
        return builder.toString();
    }

    protected void populateRecatchPa(HttpSession session, Model model, boolean b) {
        session.setAttribute("captcha.privateKey", captchaService.getPrivateKey());
        session.setAttribute("captcha.publicKey", captchaService.getPublicKey());
        model.addAttribute("invalidRecaptcha", !b);
    }
}

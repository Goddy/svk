package be.spring.app.controller;

import be.spring.app.form.ContactForm;
import be.spring.app.validators.ContactFormValidator;
import net.tanesha.recaptcha.ReCaptchaResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.validation.Valid;
import java.util.Locale;

/**
 * Created by u0090265 on 12/31/14.
 */
@Controller
public class ContactController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(ContactController.class);
    private static final String LANDING_CONTACTFORM = "contact";

    @Autowired
    ContactFormValidator validator;

    @InitBinder("form")
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(validator);
    }

    @RequestMapping(value = "/contact", method = RequestMethod.GET)
    public String getContact(@ModelAttribute("form") ContactForm form, Model model) {
        populateRecatchPa(model, true);
        return LANDING_CONTACTFORM;
    }

    @RequestMapping(value = "/contact", method = RequestMethod.POST)
    public String postContact(@Valid @ModelAttribute("form") ContactForm form, BindingResult result,
                              @RequestParam("recaptcha_challenge_field") String challangeField,
                              @RequestParam("recaptcha_response_field") String responseField,
                              Model model,
                              ServletRequest servletRequest,
                              Locale locale) {

        ReCaptchaResponse r = catchPaService.checkResponse(servletRequest, challangeField, responseField);

        if (r.isValid() && !result.hasErrors()) {
            if (!mailService.sendPreConfiguredMail(String.format("%s</br></br>Van/From: %s", form.getMessage(), form.getEmail()))) {
                throw new RuntimeException("Error sending mail");
            }
            setSuccessMessage(model, locale, "success.message.sent", null);
        } else {
            model.addAttribute("form", form);
        }

        populateRecatchPa(model, r.isValid());

        return LANDING_CONTACTFORM;
    }
}

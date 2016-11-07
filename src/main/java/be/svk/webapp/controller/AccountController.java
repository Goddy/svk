package be.svk.webapp.controller;

import be.svk.webapp.data.SocialMediaEnum;
import be.svk.webapp.form.RegistrationForm;
import be.svk.webapp.model.Account;
import be.svk.webapp.service.AccountService;
import be.svk.webapp.validators.RegistrationFormValidator;
import net.tanesha.recaptcha.ReCaptchaResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Locale;

@Controller
@RequestMapping("/account")
public class AccountController extends AbstractController {

    private static final Logger log = LoggerFactory.getLogger(AccountController.class);
    private static final String LANDING_REG_FORM = "account/register";
    private static final String REDIRECT_REGISTRATION_OK = "redirect:registration_ok.html";
    private static final String LANDING_REGISTRATION_OK = "/account/registration_ok";
    private static final String LANDING_ADD_SOCIAL_CONNECTION = "/account/addSocialConnection";
    @Autowired
    RegistrationFormValidator validator;
    @Autowired
    ProviderSignInUtils providerSignInUtils;
    @Autowired
    private AccountService accountService;

    private static Account toAccount(RegistrationForm form) {
        Account.Builder b = new Account.Builder()
                .firstName(form.getFirstName())
                .lastName(form.getLastName())
                .signInProvider(form.getSignInProvider())
                .username(form.getUsername());
        return b.build();
    }

    private static void convertPasswordError(BindingResult result) {
        for (ObjectError error : result.getGlobalErrors()) {
            String msg = error.getDefaultMessage();
            if ("account.password.mismatch.message".equals(msg)) {
                if (!result.hasFieldErrors("password")) {
                    result.rejectValue("password", "error.mismatch.account.password");
                }
            }
        }
    }

    @InitBinder("form")
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(validator);
        //Set allowed fields
        binder.setAllowedFields("oldPassword", "newPassword", "password", "confirmPassword", "firstName",
                "lastName", "username", "signInProvider");
    }

    @RequestMapping(value = "notloggedin", method = RequestMethod.GET)
    public String notLoggedIn() {
        return "notloggedin";
    }

    /**
    @RequestMapping(value = "register", method = RequestMethod.GET)
    public String getRegistrationForm(Model model) {
        model.addAttribute("Account", new RegistrationForm());
        populateRecatchPa(model, true);
        log.info("Created RegistrationForm");
        return LANDING_REG_FORM;
    }
     **/

    @RequestMapping(value = "register", method = RequestMethod.GET)
    public String showRegistrationForm(HttpServletRequest httpServletRequest, WebRequest request, Model model) {
        populateRecatchPa(httpServletRequest.getSession(), model, true);
        Connection<?> connection = providerSignInUtils.getConnectionFromSession(request);
        RegistrationForm registrationForm = createRegistrationDTO(connection);
        model.addAttribute("form", registrationForm);

        return LANDING_REG_FORM;
    }

    private RegistrationForm createRegistrationDTO(Connection<?> connection) {
        RegistrationForm dto = new RegistrationForm();

        if (connection != null) {
            UserProfile socialMediaProfile = connection.fetchUserProfile();
            dto.setUsername(socialMediaProfile.getEmail());
            dto.setFirstName(socialMediaProfile.getFirstName());
            dto.setLastName(socialMediaProfile.getLastName());

            ConnectionKey providerKey = connection.getKey();
            dto.setSignInProvider(SocialMediaEnum.valueOf(providerKey.getProviderId().toUpperCase()));
        }

        return dto;
    }

    @RequestMapping(value = "registration_ok", method = RequestMethod.GET)
    public String getRegistrationOk() {
        return LANDING_REGISTRATION_OK;
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public String postRegistrationForm(@ModelAttribute("form") @Valid RegistrationForm form, BindingResult result,
                                       Locale locale,
                                       ServletRequest servletRequest,
                                       WebRequest request,
                                       HttpServletRequest httpServletRequest,
                                       @RequestParam("recaptcha_challenge_field") String challengeField,
                                       @RequestParam("recaptcha_response_field") String responseField,
                                       Model model) {
        ReCaptchaResponse r = captchaService.checkResponse(servletRequest, challengeField, responseField);

        if (r.isValid() && !result.hasErrors()) {
            Account account = accountService.registerAccount(
                    toAccount(form), form.getPassword());
            convertPasswordError(result);
            populateRecatchPa(httpServletRequest.getSession(), model, r.isValid());
            providerSignInUtils.doPostSignUp(account.getUsername(), request);
            log.info(String.format("Account %s created", form.getUsername()));
            return REDIRECT_REGISTRATION_OK;
        } else {
            populateRecatchPa(httpServletRequest.getSession(), model, r.isValid());
            return LANDING_REG_FORM;
        }
    }
}

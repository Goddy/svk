package be.spring.app.controller;

import be.spring.app.data.SocialMediaEnum;
import be.spring.app.form.AccountDetailsForm;
import be.spring.app.form.ChangePwdForm;
import be.spring.app.form.RegistrationForm;
import be.spring.app.model.Account;
import be.spring.app.service.AccountService;
import be.spring.app.validators.RegistrationFormValidator;
import com.google.common.base.Strings;
import net.tanesha.recaptcha.ReCaptchaResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
import javax.validation.Valid;
import java.util.Locale;

@Controller
@RequestMapping("/account")
public class AccountController extends AbstractController {

    private static final Logger log = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    RegistrationFormValidator validator;

    @Autowired
    ProviderSignInUtils providerSignInUtils;

    @InitBinder("form")
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(validator);
        //Set allowed fields
        binder.setAllowedFields("oldPassword", "newPassword", "password", "confirmPassword", "firstName",
                "lastName", "username", "signInProvider");
    }

    @Autowired
    private AccountService accountService;
    private static final String LANDING_REG_FORM = "forms/registrationForm";
    private static final String LANDING_DET_FORM = "forms/accountDetailsForm";
    private static final String REDIRECT_REGISTRATION_OK = "redirect:registration_ok.html";
    private static final String LANDING_REGISTRATION_OK = "/account/registration_ok";
    private static final String LANDING_ADD_SOCIAL_CONNECTION = "/account/addSocialConnection";

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
    public String showRegistrationForm(WebRequest request, Model model) {
        populateRecatchPa(model, true);
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
    public String postRegistrationForm(@ModelAttribute("form") @Valid RegistrationForm form, BindingResult result, Locale locale,
                                       ServletRequest servletRequest,
                                       WebRequest request,
                                       @RequestParam("recaptcha_challenge_field") String challengeField,
                                       @RequestParam("recaptcha_response_field") String responseField,
                                       Model model) {
        ReCaptchaResponse r = catchPaService.checkResponse(servletRequest, challengeField, responseField);

        if (r.isValid() && !result.hasErrors()) {
            Account account = accountService.registerAccount(
                    toAccount(form), form.getPassword());
            convertPasswordError(result);
            populateRecatchPa(model, r.isValid());
            providerSignInUtils.doPostSignUp(account.getUsername(), request);
            log.info(String.format("Account %s created", form.getUsername()));
            return REDIRECT_REGISTRATION_OK;
        }
        else {
            populateRecatchPa(model, r.isValid());
            return LANDING_REG_FORM;
        }
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "update_details", method = RequestMethod.POST)
    public String updateAccountDetails(@ModelAttribute("Account") @Valid AccountDetailsForm form, BindingResult result, Model model, Locale locale) {
        Account a = getAccountFromSecurity();
        if (!result.hasErrors()) {
            accountService.updateAccount(a, result, form);
            log.info(String.format("Updated account %s", a.getUsername()));
            if (!result.hasErrors()) {
                setSuccessMessage(model, locale, "success.changedDetails", null);
            }
        }
        setAccountDetailsModel(a, model, form, new ChangePwdForm());
        return LANDING_DET_FORM;
    }


    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public String getAccountDetails(Model model) {
        log.info("Method edit called");
        Account activeAccount = getAccountFromSecurity();
        setAccountDetailsModel(activeAccount, model, new AccountDetailsForm(), new ChangePwdForm());

        log.info("Created AccountDetailsForm");
        return LANDING_DET_FORM;
    }

    private void setAccountDetailsModel(Account account, Model model, AccountDetailsForm accountDetailsForm, ChangePwdForm passwordForm) {
        Account currentAccount = accountService.getAccount(account.getId());
        accountDetailsForm.setFirstName(currentAccount.getFirstName());
        accountDetailsForm.setUsername(currentAccount.getUsername());
        accountDetailsForm.setLastName(currentAccount.getLastName());
        accountDetailsForm.setHasSignInProvider(currentAccount.getSignInProvider() != null);
        accountDetailsForm.setHasPassword(!accountService.passwordIsNullOrEmpty(currentAccount));
        accountDetailsForm.setDoodleNotificationMails(currentAccount.getAccountSettings().isSendDoodleNotifications());
        accountDetailsForm.setNewsNotificationMails(currentAccount.getAccountSettings().isSendNewsNotifications());

        model.addAttribute("Account", accountDetailsForm);
        model.addAttribute("changePassword", passwordForm);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "changePassword", method = RequestMethod.POST)
    public
    String updatePassword(@ModelAttribute("changePassword") @Valid ChangePwdForm form, BindingResult result, Locale locale, Model model) {
        log.info("updatePassword.json called");
        Account activeAccount = getAccountFromSecurity();
        //Check old pwd
        if (Strings.isNullOrEmpty(form.getOldPassword()) || !accountService.checkOldPassword(activeAccount, form.getOldPassword())) {
            result.rejectValue("oldPassword", "validation.oldpwd.nomatch");
        }

        if (!result.hasErrors()) {
            try {
                accountService.setPasswordFor(activeAccount, form.getNewPassword());
                setSuccessMessage(model, locale, "success.changePassword", null);
            } catch (Exception e) {
                setErrorMessage(model, locale, "error.unknown", null);
            }
        }
        setAccountDetailsModel(activeAccount, model, new AccountDetailsForm(), form);
        return LANDING_DET_FORM;
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "setPassword", method = RequestMethod.POST)
    public
    String setPassword(@ModelAttribute("changePassword") @Valid ChangePwdForm form, BindingResult result, Locale locale, Model model) {
        log.info("setPassword.json called");
        Account activeAccount = getAccountFromSecurity();
        //Check pwd complexity
        if (!result.hasErrors()) {
            try {
                if (accountService.passwordIsNullOrEmpty(activeAccount)) {
                    accountService.setPasswordFor(activeAccount, form.getNewPassword());
                    setSuccessMessage(model, locale, "success.changePassword", null);
                }
                else {
                    //Not permitted, old pw detected
                    setErrorMessage(model, locale, "error.unknown", null);
                }
            } catch (Exception e) {
                setErrorMessage(model, locale, "error.unknown", null);
            }
        }
        setAccountDetailsModel(activeAccount, model, new AccountDetailsForm(), form);
        return LANDING_DET_FORM;
    }

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
}

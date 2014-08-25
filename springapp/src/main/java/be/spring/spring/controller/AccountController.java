package be.spring.spring.controller;

import be.spring.spring.form.AccountDetailsForm;
import be.spring.spring.form.ChangePwdForm;
import be.spring.spring.form.RegistrationForm;
import be.spring.spring.interfaces.AccountService;
import be.spring.spring.model.Account;
import be.spring.spring.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Locale;

@Controller
@RequestMapping("/account")
public class AccountController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(AccountController.class);
    @Autowired
    private AccountService accountService;
    private static final String VN_REG_FORM = "forms/registrationForm";
    private static final String VN_DET_FORM = "forms/accountDetailsForm";
    private static final String VN_REG_OK = "redirect:registration_ok";
    private static final String VN_DET_OK = "redirect:/account/update_ok";

    @RequestMapping(value = "notloggedin", method = RequestMethod.GET)
    public String notLoggedIn() {
        return "notloggedin";
    }

    @RequestMapping(value = "register", method = RequestMethod.GET)
    public String getRegistrationForm(Model model) {
        model.addAttribute("Account", new RegistrationForm());
        log.info("Created RegistrationForm");
        return VN_REG_FORM;
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public String postRegistrationForm(@ModelAttribute("Account") @Valid RegistrationForm form, BindingResult result) {
        log.info("Posted RegistrationForm: {}", form);
        accountService.registerAccount(
                toAccount(form), form.getPassword(), result);
        convertPasswordError(result);
        return (result.hasErrors() ? VN_REG_FORM : VN_REG_OK);
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "update_details", method = RequestMethod.POST)
    public String updateAccountDetails(@ModelAttribute("Account") @Valid AccountDetailsForm form, BindingResult result, Model model) {
        Account account = toAccount(form);
        accountService.updateAccount(account, result);
        log.info(String.format("Updated account %s", account.getUsername()));
        model.addAttribute("changePassword", new ChangePwdForm());
        return (result.hasErrors() ? VN_DET_FORM : VN_DET_OK);

    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public String getAccountDetails(Model model) {
        log.info("Method edit called");
        Account activeAccount = getAccountFromSecurity();

        AccountDetailsForm accountDetailsForm = new AccountDetailsForm();
        accountDetailsForm.setFirstName(activeAccount.getFirstName());
        accountDetailsForm.setUsername(activeAccount.getUsername());
        accountDetailsForm.setLastName(activeAccount.getLastName());

        model.addAttribute("Account", accountDetailsForm);
        model.addAttribute("changePassword", new ChangePwdForm());
        //model.addAttribute("Password", new ChangePwdForm());

        log.info("Created AccountDetailsForm");
        return VN_DET_FORM;
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "changePassword.json", method = RequestMethod.POST)
    public @ResponseBody String updatePassword(@ModelAttribute("changePassword") ChangePwdForm form, Locale locale) {
        log.info("updatePassword.json called");
        Account activeAccount = getAccountFromSecurity();
        //Check pwd complexity
        if (!form.getNewPassword().matches(Constants.PASSWORD_REGEX)) {
            return getMessage("validation.complexity.password.message", null, locale);
        }
        try {
            accountService.setPasswordFor(activeAccount, form.getNewPassword());
            return getMessage("success.changePassword", null, locale);
        } catch (Exception e) {
            return getMessage("error.unknown", null, locale);
        }
    }

    private static Account toAccount(RegistrationForm form) {
        Account account = new Account();
        account.setFirstName(form.getFirstName());
        account.setLastName(form.getLastName());
        account.setUsername(form.getUsername());
        return account;
    }

    private Account toAccount(AccountDetailsForm form) {
        Account account = getAccountFromSecurity();
        account.setFirstName(form.getFirstName());
        account.setLastName(form.getLastName());
        account.setUsername(form.getUsername());
        return account;
    }

    @InitBinder
    /** Sets allowed fields that can be submitted
     * @param binder
     */
    public void initBinder(WebDataBinder binder) {
        binder.setAllowedFields("oldPassword", "newPassword", "password", "confirmPassword", "firstName",
                "lastName", "username");
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

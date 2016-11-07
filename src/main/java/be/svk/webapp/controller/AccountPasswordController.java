package be.svk.webapp.controller;

import be.svk.webapp.form.AccountProfileForm;
import be.svk.webapp.form.ChangePwdForm;
import be.svk.webapp.model.Account;
import be.svk.webapp.service.AccountService;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.Locale;

/**
 * Created by u0090265 on 11/11/15.
 */
@Controller
@RequestMapping("/account")
public class AccountPasswordController extends AbstractAccountController {

    private static final Logger log = LoggerFactory.getLogger(AccountController.class);

    private static final String LANDING_DET_FORM = "account/profile";

    @Autowired
    AccountService accountService;


    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "changePassword", method = RequestMethod.POST)
    public String updatePassword(@ModelAttribute("changePassword") @Valid ChangePwdForm form, BindingResult result, Locale locale, Model model) {
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
        setAccountDetailsModel(activeAccount, model, new AccountProfileForm(), form);
        return LANDING_DET_FORM;
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "setPassword", method = RequestMethod.POST)
    public String setPassword(@ModelAttribute("changePassword") @Valid ChangePwdForm form, BindingResult result, Locale locale, Model model) {
        log.info("setPassword.json called");
        Account activeAccount = getAccountFromSecurity();
        //Check pwd complexity
        if (!result.hasErrors()) {
            try {
                if (accountService.passwordIsNullOrEmpty(activeAccount)) {
                    accountService.setPasswordFor(activeAccount, form.getNewPassword());
                    setSuccessMessage(model, locale, "success.changePassword", null);
                } else {
                    //Not permitted, old pw detected
                    setErrorMessage(model, locale, "error.unknown", null);
                }
            } catch (Exception e) {
                setErrorMessage(model, locale, "error.unknown", null);
            }
        }
        setAccountDetailsModel(activeAccount, model, new AccountProfileForm(), form);
        return LANDING_DET_FORM;
    }
}

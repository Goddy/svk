package be.spring.app.controller;

import be.spring.app.form.AccountProfileForm;
import be.spring.app.form.ChangePwdForm;
import be.spring.app.model.Account;
import be.spring.app.model.AccountProfile;
import be.spring.app.service.AccountService;
import be.spring.app.service.ImageService;
import be.spring.app.validators.AccountProfileFormValidator;
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
public class AccountProfileController extends AbstractController {

    private static final Logger log = LoggerFactory.getLogger(AccountController.class);

    private static final String LANDING_DET_FORM = "forms/accountDetailsForm";

    @Autowired
    AccountProfileFormValidator validator;

    @Autowired
    AccountService accountService;

    @Autowired
    ImageService imageService;

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

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public String getAccountDetails(Model model) {
        log.info("Method edit called");
        Account activeAccount = getAccountFromSecurity();
        setAccountDetailsModel(activeAccount, model, new AccountProfileForm(), new ChangePwdForm());

        log.info("Created AccountDetailsForm");
        return LANDING_DET_FORM;
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "update_details", method = RequestMethod.POST)
    public String updateAccountDetails(@ModelAttribute("accountProfileForm") @Valid AccountProfileForm form, BindingResult result, Model model, Locale locale) {
        Account a = getAccountFromSecurity();
        //Extra validation check!
        accountService.validateUsernameExcludeCurrentId(form.getUsername(), a.getId(), result);
        if (!result.hasErrors()) {
            accountService.updateAccount(a, form);
            log.info(String.format("Updated account %s", a.getUsername()));
            if (!result.hasErrors()) {
                setSuccessMessage(model, locale, "success.changedDetails", null);
            }
        }
        setAccountDetailsModel(a, model, form, new ChangePwdForm());
        return LANDING_DET_FORM;
    }


    private void setAccountDetailsModel(Account account, Model model, AccountProfileForm accountProfileForm, ChangePwdForm passwordForm) {
        Account currentAccount = accountService.getAccount(account.getId());
        accountProfileForm.setFirstName(currentAccount.getFirstName());
        accountProfileForm.setUsername(currentAccount.getUsername());
        accountProfileForm.setLastName(currentAccount.getLastName());
        accountProfileForm.setHasSignInProvider(currentAccount.getSignInProvider() != null);
        accountProfileForm.setHasPassword(!accountService.passwordIsNullOrEmpty(currentAccount));
        accountProfileForm.setDoodleNotificationMails(currentAccount.getAccountSettings().isSendDoodleNotifications());
        accountProfileForm.setNewsNotificationMails(currentAccount.getAccountSettings().isSendNewsNotifications());

        if (account.getAccountProfile() != null) {
            AccountProfile accountProfile = account.getAccountProfile();
            accountProfileForm.setMobilePhone(accountProfile.getMobilePhone());
            accountProfileForm.setPhone(accountProfile.getPhone());
            accountProfileForm.setAvatarUrl(accountProfile.getAvatar() != null ? accountProfile.getAvatar().getImageUrl() : null);
        }

        model.addAttribute("accountProfileForm", accountProfileForm);
        model.addAttribute("changePassword", passwordForm);
    }
}

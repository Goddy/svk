package be.svk.webapp.controller;

import be.svk.webapp.data.PositionsEnum;
import be.svk.webapp.form.AccountProfileForm;
import be.svk.webapp.form.ChangePwdForm;
import be.svk.webapp.model.Account;
import be.svk.webapp.service.AccountService;
import be.svk.webapp.service.ImageService;
import be.svk.webapp.validators.AccountProfileFormValidator;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by u0090265 on 11/11/15.
 */
@Controller
@RequestMapping("/account")
public class AccountProfileController extends AbstractAccountController {

    private static final Logger log = LoggerFactory.getLogger(AccountController.class);

    private static final String LANDING_PROFILE = "account/profile";
    private static final String LANDING_EDIT_PROFILE = "account/editProfile";
    private static final String LANDING_ACCOUNT_OVERVIEW = "account/overview";

    @Autowired
    AccountProfileFormValidator validator;

    @Autowired
    AccountService accountService;

    @Autowired
    ImageService imageService;

    @InitBinder("accountProfileForm")
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(validator);
    }

    @ModelAttribute(value = "positions")
    public Map<String, String> getPositions(Locale locale) {
        Map<String, String> m = Maps.newLinkedHashMap();
        m.put(PositionsEnum.GOALKEEPER.name(), getMessage("position." + PositionsEnum.GOALKEEPER.name().toLowerCase(), null, locale));
        m.put(PositionsEnum.DEFENDER.name(), getMessage("position." + PositionsEnum.DEFENDER.name().toLowerCase(), null, locale));
        m.put(PositionsEnum.MIDFIELDER.name(), getMessage("position." + PositionsEnum.MIDFIELDER.name().toLowerCase(), null, locale));
        m.put(PositionsEnum.FORWARD.name(), getMessage("position." + PositionsEnum.FORWARD.name().toLowerCase(), null, locale));
        return m;
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public String getAccountDetails(Model model) {
        log.info("Method edit called");
        Account activeAccount = getAccountFromSecurity();
        setAccountDetailsModel(activeAccount, model, new AccountProfileForm(), new ChangePwdForm());

        log.info("Created AccountDetailsForm");
        return LANDING_PROFILE;
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public String updateAccountDetails(@ModelAttribute("changePassword") ChangePwdForm passwordForm, @ModelAttribute("accountProfileForm") @Valid AccountProfileForm form, BindingResult result, Model model, Locale locale) {
        Account a = getAccountFromSecurity();

        if (!result.hasErrors()) {
            Account resultAccount = accountService.updateAccount(a, form);
            log.info(String.format("Updated account %s", a.getUsername()));
            setSuccessMessage(model, locale, "success.changedDetails", null);
            setAccountDetailsModel(resultAccount, model, form, passwordForm);
        }
        return LANDING_PROFILE;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "adminUpdate", method = RequestMethod.POST)
    public String updateAccountDetailsAsAdmin(@ModelAttribute("accountProfileForm") @Valid AccountProfileForm form, BindingResult result, Model model, Locale locale) {
        //admin edits, so get account id from form
        Account a = accountService.getAccount(form.getAccountId());
        Account admin = getAccountFromSecurity();

        if (!result.hasErrors()) {
            Account resultAccount = accountService.updateAccount(a, form);
            log.info(String.format("Updated account %s", admin.getUsername()));
            setSuccessMessage(model, locale, "success.changedDetails", null);
            setAccountDetailsModel(resultAccount, model, form);
        }
        return LANDING_EDIT_PROFILE;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "editProfile", method = RequestMethod.GET)
    public String getAccountDetailsAdmin(Model model, @RequestParam("id") long accountId) {
        log.info("Method edit called by admin");
        Account a = accountService.getAccount(accountId);
        AccountProfileForm accountProfileForm = new AccountProfileForm();
        accountProfileForm.setAccountId(accountId);
        setAccountDetailsModel(a, model, accountProfileForm);

        log.info("Created AccountDetailsForm");
        return LANDING_EDIT_PROFILE;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "overview", method = RequestMethod.GET)
    public String getAccountDetailsAdmin(ModelMap model) {
        List<Account> activeAccounts = accountService.getAllActivateAccounts();
        model.put("accounts", activeAccounts);
        return LANDING_ACCOUNT_OVERVIEW;
    }
}

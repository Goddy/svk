package be.spring.app.controller;

import be.spring.app.data.PositionsEnum;
import be.spring.app.form.AccountProfileForm;
import be.spring.app.form.ChangePwdForm;
import be.spring.app.model.Account;
import be.spring.app.service.AccountService;
import be.spring.app.service.ImageService;
import be.spring.app.validators.AccountProfileFormValidator;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.Locale;
import java.util.Map;

/**
 * Created by u0090265 on 11/11/15.
 */
@Controller
@RequestMapping("/account")
public class AccountProfileController extends AbstractAccountController {

    private static final Logger log = LoggerFactory.getLogger(AccountController.class);

    private static final String LANDING_DET_FORM = "account/profile";

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
        return LANDING_DET_FORM;
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public String updateAccountDetails(@ModelAttribute("changePassword") ChangePwdForm passwordForm, @ModelAttribute("accountProfileForm") @Valid AccountProfileForm form, BindingResult result, Model model, Locale locale) {
        Account a = getAccountFromSecurity();

        if (!result.hasErrors()) {
            accountService.updateAccount(a, form);
            log.info(String.format("Updated account %s", a.getUsername()));
            setSuccessMessage(model, locale, "success.changedDetails", null);
            setAccountDetailsModel(a, model, form, passwordForm);
        }
        return LANDING_DET_FORM;
    }
}

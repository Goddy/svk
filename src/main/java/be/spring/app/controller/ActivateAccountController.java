package be.spring.app.controller;

import be.spring.app.controller.exceptions.ObjectNotFoundException;
import be.spring.app.form.ActivateAccountForm;
import be.spring.app.model.Account;
import be.spring.app.service.AccountService;
import be.spring.app.utils.GeneralUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Locale;

/**
 * Created by u0090265 on 9/19/14.
 */
@Controller
@RequestMapping("/account")
public class ActivateAccountController extends AbstractController {
    private static final String LANDING_ACTIVATION = "/account/activateAccount";

    @Value("${base.url}")
    private String baseUrl;

    private static final Logger log = LoggerFactory.getLogger(ActivateAccountController.class);

    @Autowired
    private AccountService accountService;

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "activateAccount", method = RequestMethod.GET)
    public String getActivateAccount(@ModelAttribute("form") ActivateAccountForm form, @RequestParam String accountId, Model model, Locale locale) {
        Account account = accountService.getAccount(accountId);
        if (account == null)
            throw new ObjectNotFoundException(String.format("Account with id %s does not exist", accountId));

        model.addAttribute("account", account);

        if (account.isActive()) {
            setErrorMessage(model, locale, "error.activation.already.activated", null);
            return LANDING_ACTIVATION;
        }
        form.setAccountId(GeneralUtils.convertToLong(accountId));

        return LANDING_ACTIVATION;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "activateAccount", method = RequestMethod.POST)
    public String postActivateAccount(@Valid @ModelAttribute("form") ActivateAccountForm form, BindingResult result, Model model, Locale locale) {
        Account a = getAccountFromSecurity();
        Account activatedAccount = accountService.activateAccount(form, locale, result);
        log.info(String.format("Account id %s was activated by %s", form.getAccountId(), a.getUsername()));
        if (!result.hasErrors()) {
            setSuccessMessage(model, locale, "success.activation", null);
        } else {
            setErrorMessage(model, locale, "validation.activation.email.not.sent", null);
        }

        model.addAttribute("account", activatedAccount);

        return LANDING_ACTIVATION;
    }
}

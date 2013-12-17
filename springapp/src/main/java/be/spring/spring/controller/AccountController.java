package be.spring.spring.controller;

import be.spring.spring.form.accountDetailsForm;
import be.spring.spring.form.changePwdForm;
import be.spring.spring.form.registrationForm;
import be.spring.spring.interfaces.AccountService;
import be.spring.spring.model.Account;
import be.spring.spring.persistence.UserDetailsAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import javax.validation.Valid;

@Controller
@RequestMapping("/account")
public class AccountController {
	private static final Logger log = LoggerFactory.getLogger(AccountController.class);
	@Inject
	private AccountService accountService;
	private static final String VN_REG_FORM = "forms/registrationForm";
    private static final String VN_DET_FORM = "forms/accountDetailsForm";
	private static final String VN_REG_OK = "redirect:registration_ok";
    private static final String VN_REDIRECT_NOT_LOGGED_IN = "redirect:notloggedin.html";
	
	@RequestMapping(value = "register", method = RequestMethod.GET)
	public String getRegistrationForm(Model model) {
		model.addAttribute("Account", new registrationForm());
		log.info("Created registrationForm");
		return VN_REG_FORM;
	}
	
	@RequestMapping(value = "register", method = RequestMethod.POST)
	public String postRegistrationForm(@ModelAttribute("Account") @Valid registrationForm form, BindingResult result) {
		log.info("Posted registrationForm: {}", form);
		accountService.registerAccount(
				toAccount(form), form.getPassword(), result);
		convertPasswordError(result);
		return (result.hasErrors() ? VN_REG_FORM : VN_REG_OK);
	}
    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public String getAccountDetails(Model model) {
        Account activeAccount = getAccountFromSecurity();

        accountDetailsForm accountDetailsForm = new accountDetailsForm();
        accountDetailsForm.setFirstName(activeAccount.getFirstName());
        accountDetailsForm.setEmail(activeAccount.getUsername());
        accountDetailsForm.setLastName(activeAccount.getLastName());

        model.addAttribute("Account", activeAccount);
        model.addAttribute("Password", new changePwdForm());
        log.info("Created accountDetailsForm");
        return VN_DET_FORM;
    }

    @RequestMapping(value = "change_details", method = RequestMethod.POST)
    public String getAccountDetails(@ModelAttribute("Account") @Valid accountDetailsForm form, BindingResult result) {
        log.info("Posted registrationForm: {}", form);
        accountService.updateAccount(toAccount(form), result);
        return (result.hasErrors() ? VN_REG_FORM : VN_REG_OK);
    }
	
	private static Account toAccount(registrationForm form) {
		Account account = new Account();
		account.setFirstName(form.getFirstName());
		account.setLastName(form.getLastName());
		account.setUsername(form.getEmail());
		return account;
	}

    private static Account toAccount(accountDetailsForm form) {
        Account account = getAccountFromSecurity();
        account.setFirstName(form.getFirstName());
        account.setLastName(form.getLastName());
        account.setUsername(form.getEmail());
        return account;
    }
    private static Account getAccountFromSecurity()
    {
        UserDetailsAdapter details = (UserDetailsAdapter)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return details.getAccount();
    }
	
	@InitBinder
	/** Sets allowed fields that can be submitted
	 * @param binder
	 */
	public void initBinder(WebDataBinder binder) {
	binder.setAllowedFields(new String[] {
	"oldPassword", "newPassword", "password", "confirmPassword", "firstName",
	"lastName", "email" });
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

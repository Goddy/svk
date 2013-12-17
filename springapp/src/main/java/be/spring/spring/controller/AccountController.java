package be.spring.spring.controller;

import javax.inject.Inject;
import javax.validation.Valid;

import be.spring.spring.form.accountDetailsForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import be.spring.spring.form.registrationForm;
import be.spring.spring.model.Account;
import be.spring.spring.interfaces.AccountService;

@Controller
@RequestMapping("/account")
public class AccountController {
	private static final Logger log = LoggerFactory.getLogger(AccountController.class);
	@Inject
	private AccountService accountService;
	private static final String VN_REG_FORM = "forms/registrationForm";
    private static final String VN_DET_FORM = "forms/accountDetailsForm";
	private static final String VN_REG_OK = "redirect:registration_ok";
	
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
    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public String getAccountDetails(Model model) {
        model.addAttribute("Account", new accountDetailsForm());
        log.info("Created accountDetailsForm");
        return VN_DET_FORM;
    }

    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public String getAccountDetails(@ModelAttribute("Account") @Valid registrationForm form, BindingResult result) {
        log.info("Posted registrationForm: {}", form);
        accountService.registerAccount(
                toAccount(form), form.getPassword(), result);
        convertPasswordError(result);
        return (result.hasErrors() ? VN_REG_FORM : VN_REG_OK);
    }
	
	private static Account toAccount(registrationForm form) {
		Account account = new Account();
		account.setFirstName(form.getFirstName());
		account.setLastName(form.getLastName());
		account.setUsername(form.getEmail());
		return account;
	}
	
	@InitBinder
	/** Sets allowed fields that can be submitted
	 * @param binder
	 */
	public void initBinder(WebDataBinder binder) {
	binder.setAllowedFields(new String[] {
	"password", "confirmPassword", "firstName",
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

package be.spring.spring.controller;

import javax.validation.Valid;

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

import form.registrationForm;
@Controller
@RequestMapping("/register")
public class AccountController {
	private static final Logger log = LoggerFactory.getLogger(AccountController.class);
	private static final String VN_REG_FORM = "forms/registrationForm";
	private static final String VN_REG_OK = "redirect:registration_ok";
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String getRegistrationForm(Model model) {
		model.addAttribute("Account", new registrationForm());
		log.info("Created registrationForm");
		return VN_REG_FORM;
	}
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	public String postRegistrationForm(@ModelAttribute("Account") @Valid registrationForm form, BindingResult result) {
		log.info("Posted registrationForm: {}", form);
		convertPasswordError(result);
		return (result.hasErrors() ? VN_REG_FORM : VN_REG_OK);
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

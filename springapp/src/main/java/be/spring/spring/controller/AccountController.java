package be.spring.spring.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import form.registrationForm;
@Controller
@RequestMapping("/register")
public class AccountController {
	private static final Logger log = LoggerFactory.getLogger(AccountController.class);
	
	@RequestMapping(value = "register", method = RequestMethod.GET)
	public String getRegistrationForm(Model model) {
		model.addAttribute("Account", new registrationForm());
		return "forms/registrationForm";
	}
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	public String postRegistrationForm(registrationForm form) {
		log.info("Created registration: {}", form);
		return "redirect:registration_ok";
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
}

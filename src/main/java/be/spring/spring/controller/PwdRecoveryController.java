package be.spring.spring.controller;

import be.spring.spring.form.PwdRecoveryForm;
import be.spring.spring.interfaces.PwdRecoveryService;
import be.spring.spring.validators.PwdRecoveryValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Locale;

/**
 * Created by u0090265 on 9/11/14.
 */
@Controller
@RequestMapping("/account")
public class PwdRecoveryController extends AbstractController {
    @Autowired
    PwdRecoveryService service;

    @Autowired
    PwdRecoveryValidator validator;

    private final static String GET_PWD_REC_CODE_LANDING = "/account/getPwdRecoveryCode";

    @InitBinder("form")
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(validator);
    }


    @RequestMapping(value = "getPwdRecoveryCode", method = RequestMethod.GET)
    public String getPwdRecoveryForm(@ModelAttribute("form") PwdRecoveryForm form, @RequestParam(value = "code", required = false) String code, @RequestParam(value = "email", required = false) String email) {
        if (code != null && email != null) {
            form.setNewCode(false);
            form.setEmail(email);
            form.setCode(code);
        }
        return GET_PWD_REC_CODE_LANDING;
    }

    @RequestMapping(value = "getPwdRecoveryCode", method = RequestMethod.POST)
    public String postPwdRecoveryForm(@Valid @ModelAttribute("form") PwdRecoveryForm form, BindingResult result ,ModelMap model, Locale locale) {
        return form.isNewCode() ? getNewCode(form, result, locale, model) : checkCode(form, result, locale, model);
    }

    private String getNewCode(PwdRecoveryForm form, BindingResult result, Locale locale, ModelMap model) {
        if (result.hasErrors()) return GET_PWD_REC_CODE_LANDING;
        service.setRecoveryCodeAndEmail(form.getEmail(), result, locale);
        if (!result.hasErrors()) {
            setSuccessMessage(model, locale, "success.pwd.recovery.sent", new String[]{form.getEmail()});
        }
        return GET_PWD_REC_CODE_LANDING;
    }

    private String checkCode(PwdRecoveryForm form, BindingResult result, Locale locale, ModelMap model) {
        if (result.hasErrors()) return GET_PWD_REC_CODE_LANDING;
        service.checkPwdRecoverCodeAndEmail(form.getNewPassword(), form.getEmail(), form.getCode(), result, locale);
        if (!result.hasErrors()) {
            form.setEmail(null);
            form.setCode(null);
            setSuccessMessage(model, locale, "success.changePassword", new String[]{form.getEmail()});
        }
        return GET_PWD_REC_CODE_LANDING;
    }

}

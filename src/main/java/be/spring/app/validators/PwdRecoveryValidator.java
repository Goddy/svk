package be.spring.app.validators;

import be.spring.app.form.PwdRecoveryForm;
import be.spring.app.interfaces.AccountService;
import be.spring.app.utils.ValidationHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Created by u0090265 on 9/12/14.
 */
@Component
public class PwdRecoveryValidator implements Validator {
    @Autowired
    AccountService accountService;

    @Override
    public boolean supports(Class<?> aClass) {
        return PwdRecoveryForm.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        PwdRecoveryForm form = (PwdRecoveryForm) o;
        ValidationUtils.rejectIfEmpty(errors, "email", "validation.notempty.message");

        if (form.isNewCode()) {
            if (form.getEmail() != null && accountService.getActiveAccountByEmail(form.getEmail()) == null) {
                errors.rejectValue("email", "validation.email.not.exists");
            }
        }
        else {
            ValidationUtils.rejectIfEmpty(errors, "code", "validation.notempty.message");
            ValidationUtils.rejectIfEmpty(errors, "newPassword", "validation.notempty.message");
            ValidationUtils.rejectIfEmpty(errors, "repeatNewPassword", "validation.notempty.message");

            if (!form.getNewPassword().equals(form.getRepeatNewPassword())) {
                errors.rejectValue("newPassword", "validation.passwords.noMatch");
            }
            if (!ValidationHelper.isMatch(form.getNewPassword()))
                errors.rejectValue("newPassword", "validation.complexity.password.message");

        }
    }
}

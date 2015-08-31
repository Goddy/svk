package be.spring.app.validators;

import be.spring.app.form.RegistrationForm;
import be.spring.app.service.AccountService;
import be.spring.app.utils.Constants;
import be.spring.app.utils.GeneralUtils;
import be.spring.app.utils.ValidationHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Created by u0090265 on 4/7/15.
 */
@Component
public class RegistrationFormValidator implements Validator {
    @Autowired
    AccountService accountService;
    @Override
    public boolean supports(Class<?> aClass) {
        return RegistrationForm.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        RegistrationForm form = (RegistrationForm) o;
        sanitize(form);

        ValidationUtils.rejectIfEmpty(errors, "username", "validation.notempty.message");
        ValidationUtils.rejectIfEmpty(errors, "firstName", "validation.notempty.message");
        ValidationUtils.rejectIfEmpty(errors, "lastName", "validation.notempty.message");

        if (!ValidationHelper.isNameMatch(form.getFirstName())) {
            errors.rejectValue("firstName", "validation.name.mismatch");
        }

        if (!ValidationHelper.isLength(form.getFirstName(), Constants.TWO)) {
            errors.rejectValue("firstName", "validation.minimum.length", new Object[]{Constants.TWO}, "validation.minimum.length");
        }

        if (!ValidationHelper.isNameMatch(form.getLastName())) {
            errors.rejectValue("lastName", "validation.name.mismatch");
        }

        if (!ValidationHelper.isLength(form.getLastName(), Constants.TWO)) {
            errors.rejectValue("lastName", "validation.minimum.length", new Object[]{Constants.TWO}, "validation.minimum.length");
        }

        if (form.isNormalRegistration()) {
            ValidationUtils.rejectIfEmpty(errors, "confirmPassword", "validation.notempty.message");
            ValidationUtils.rejectIfEmpty(errors, "password", "validation.notempty.message");

            if (!errors.hasFieldErrors("password") && !errors.hasFieldErrors("confirmPassword")) {
                if (!form.getPassword().equals(form.getConfirmPassword())) {
                    errors.rejectValue("confirmPassword", "validation.password.mismatch.message");
                }
                if (!ValidationHelper.isPasswordMatch(form.getPassword())) {
                    errors.rejectValue("password", "validation.complexity.password.message");
                }
            }
        }

        //Check for username issues
        if (!errors.hasFieldErrors("username") ) {
            accountService.validateUsername(form.getUsername(), errors);
        }
    }

    private void sanitize(RegistrationForm form) {
        form.setFirstName(GeneralUtils.trim(form.getFirstName()));
        form.setLastName(GeneralUtils.trim(form.getLastName()));
    }
}

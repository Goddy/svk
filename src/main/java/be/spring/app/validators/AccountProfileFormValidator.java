package be.spring.app.validators;

import be.spring.app.form.AccountProfileForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by u0090265 on 11/11/15.
 */
@Component
public class AccountProfileFormValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return AccountProfileForm.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        AccountProfileForm form = (AccountProfileForm) o;

    }
}

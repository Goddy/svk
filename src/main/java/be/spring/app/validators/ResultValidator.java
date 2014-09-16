package be.spring.app.validators;

import be.spring.app.form.ChangeResultForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Created by u0090265 on 8/30/14.
 */
@Component
public class ResultValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return ChangeResultForm.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ChangeResultForm form = (ChangeResultForm) o;

        ValidationUtils.rejectIfEmpty(errors, "atGoals", "validation.notempty.message");
        ValidationUtils.rejectIfEmpty(errors, "htGoals", "validation.notempty.message");
    }
}

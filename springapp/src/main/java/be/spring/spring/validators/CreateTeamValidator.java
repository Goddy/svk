package be.spring.spring.validators;

import be.spring.spring.form.CreateTeamForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Created by u0090265 on 5/11/14.
 */
@Component
public class CreateTeamValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return CreateTeamForm.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        CreateTeamForm form = (CreateTeamForm) o;
        ValidationUtils.rejectIfEmpty(errors, "teamName", "validation.notempty.message");
        ValidationUtils.rejectIfEmpty(errors, "address", "validation.notempty.message");
        ValidationUtils.rejectIfEmpty(errors, "postalCode", "validation.notempty.message");
        ValidationUtils.rejectIfEmpty(errors, "city", "validation.notempty.message");
    }
}

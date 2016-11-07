package be.svk.webapp.validators;

import be.svk.webapp.form.ContactForm;
import be.svk.webapp.utils.ValidationHelper;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Created by u0090265 on 12/31/14.
 */
@Component
public class ContactFormValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return ContactForm.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ContactForm form = (ContactForm) o;
        ValidationUtils.rejectIfEmpty(errors, "message", "validation.notempty.message");

        if (!ValidationHelper.isEmailMatch(form.getEmail())) {
            errors.rejectValue("email", "validation.noEmail");
        }
        form.setMessage(SanitizeUtils.SanitizeHtml(form.getMessage()));
    }
}

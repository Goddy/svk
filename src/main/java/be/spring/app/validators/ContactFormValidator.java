package be.spring.app.validators;

import be.spring.app.form.ContactForm;
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

        form.setMessage(SanitizeUtils.SanitizeHtml(form.getMessage()));
    }
}

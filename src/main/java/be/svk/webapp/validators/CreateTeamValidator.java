package be.svk.webapp.validators;

import be.svk.webapp.form.CreateAndUpdateTeamForm;
import org.apache.commons.lang3.StringUtils;
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
        return CreateAndUpdateTeamForm.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        CreateAndUpdateTeamForm form = (CreateAndUpdateTeamForm) o;
        sanitizeAll(form);

        ValidationUtils.rejectIfEmpty(errors, "teamName", "validation.notempty.message");
        if (!form.isUseExistingAddress()) {
            ValidationUtils.rejectIfEmpty(errors, "address", "validation.notempty.message");
            ValidationUtils.rejectIfEmpty(errors, "postalCode", "validation.notempty.message");
            ValidationUtils.rejectIfEmpty(errors, "city", "validation.notempty.message");
            if (!StringUtils.isNumeric(form.getPostalCode())) errors.rejectValue("postalCode","validation.number.postalCode");
        }
    }

    private void sanitizeAll(CreateAndUpdateTeamForm form) {
        form.setTeamName(SanitizeUtils.SanitizeHtml(form.getTeamName()));
        form.setAddress(SanitizeUtils.SanitizeHtml(form.getAddress()));
        form.setPostalCode(SanitizeUtils.SanitizeHtml(form.getPostalCode()));
        form.setCity(SanitizeUtils.SanitizeHtml(form.getCity()));
    }
}

package be.svk.webapp.validators;

import be.svk.webapp.form.AccountForm;
import be.svk.webapp.service.AccountService;
import be.svk.webapp.utils.Constants;
import be.svk.webapp.utils.GeneralUtils;
import be.svk.webapp.utils.ValidationHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

/**
 * Created by u0090265 on 12/9/15.
 */
public abstract class AccountFormValidator {
    @Autowired
    AccountService accountService;

    protected void validateCommon(AccountForm form, Errors errors) {
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
    }

    private void sanitize(AccountForm form) {
        form.setFirstName(GeneralUtils.trim(form.getFirstName()));
        form.setLastName(GeneralUtils.trim(form.getLastName()));
    }
}

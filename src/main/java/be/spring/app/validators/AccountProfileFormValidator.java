package be.spring.app.validators;

import be.spring.app.form.AccountProfileForm;
import be.spring.app.service.AccountService;
import be.spring.app.utils.GeneralUtils;
import be.spring.app.utils.SecurityUtils;
import be.spring.app.utils.ValidationHelper;
import com.google.common.base.Strings;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Created by u0090265 on 11/11/15.
 */
@Component
public class AccountProfileFormValidator extends AccountFormValidator implements Validator {
    @Autowired
    AccountService accountService;

    @Autowired
    SecurityUtils securityUtils;

    @Override
    public boolean supports(Class<?> aClass) {
        return AccountProfileForm.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        AccountProfileForm form = (AccountProfileForm) o;
        validateCommon(form, errors);
        sanitize(form);

        if (!Strings.isNullOrEmpty(form.getAddress()) || !Strings.isNullOrEmpty(form.getPostalCode()) || !Strings.isNullOrEmpty(form.getCity())) {
            ValidationUtils.rejectIfEmpty(errors, "address", "validation.address.notempty.message");
            ValidationUtils.rejectIfEmpty(errors, "postalCode", "validation.address.notempty.message");
            ValidationUtils.rejectIfEmpty(errors, "city", "validation.address.notempty.message");
            if (!StringUtils.isNumeric(form.getPostalCode()))
                errors.rejectValue("postalCode", "validation.number.postalCode");
        }

        if (!Strings.isNullOrEmpty(form.getPhone()) && !ValidationHelper.isPhoneMatch(form.getPhone())) {
            errors.rejectValue("phone", "validation.phone.mismatch");
        }

        if (!Strings.isNullOrEmpty(form.getMobilePhone()) && !ValidationHelper.isPhoneMatch(form.getMobilePhone())) {
            errors.rejectValue("mobilePhone", "validation.phone.mismatch");
        }

        //Check for username issues
        if (!errors.hasFieldErrors("username")) {
            accountService.validateUsernameExcludeCurrentId(form.getUsername(), securityUtils.getAccountFromSecurity().getId(), errors);
        }
    }

    private void sanitize(AccountProfileForm form) {
        form.setCity(GeneralUtils.trim(form.getCity()));
        form.setAddress(GeneralUtils.trim(form.getAddress()));
        form.setPostalCode(GeneralUtils.trim(form.getPostalCode()));
        form.setPhone(GeneralUtils.trim(form.getPhone()));
        form.setMobilePhone(GeneralUtils.trim(form.getMobilePhone()));
    }
}

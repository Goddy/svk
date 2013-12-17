package be.spring.spring.form;

import be.spring.spring.utils.Constants;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.constraints.ScriptAssert;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * User: Tom De Dobbeleer
 * Date: 12/13/13
 * Time: 2:53 PM
 * Remarks: none
 */
@ScriptAssert(
        lang = "javascript",
        script = "_this.confirmPassword.equals(_this.newPassword)",
        message = "account.password.mismatch.message")
public class changePwdForm {
    private String oldPassword;
    protected String newPassword;
    protected String confirmPassword;

    @NotNull
    @Pattern(regexp = Constants.PASSWORD_REGEX, message = "{validation.complexity.password.message}")
    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String toString() {

        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("newPassword", newPassword)
                .append("confirmPassword", confirmPassword)
                .append("oldPassword", getOldPassword())
                .toString();
    }

    public String getOldPassword() {
        return oldPassword;
    }


}
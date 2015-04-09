package be.spring.app.form;

import be.spring.app.utils.Constants;
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
        message = "validation.password.mismatch.message")
public class ChangePwdForm {
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;

    @NotNull
    @Pattern(regexp = Constants.PASSWORD_REGEX, message = "{validation.complexity.newpassword.message}")
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

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }
}

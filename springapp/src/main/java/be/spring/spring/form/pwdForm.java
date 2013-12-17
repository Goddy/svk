package be.spring.spring.form;

import be.spring.spring.utils.Constants;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * User: Tom De Dobbeleer
 * Date: 12/13/13
 * Time: 2:53 PM
 * Remarks: none
 */
public class pwdForm {
    protected String password, confirmPassword;

    @NotNull
    @Pattern(regexp = Constants.PASSWORD_REGEX, message = "{validation.complexity.password.message}")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }


    public String toString() {

        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("password", password)
                .append("confirmPassword", confirmPassword)
                .toString();
    }

}

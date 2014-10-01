package be.spring.app.form;

import be.spring.app.utils.Constants;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.lang3.text.WordUtils;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.ScriptAssert;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@ScriptAssert(
        lang = "javascript",
        script = "_this.confirmPassword.equals(_this.password)",
        message = "account.password.mismatch.message")
public class RegistrationForm {

    protected String firstName, lastName, username, password, confirmPassword;

    @NotNull
    @Pattern(regexp = Constants.PASSWORD_REGEX, message = "{validation.complexity.password.message}")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NotNull
    @NotEmpty(message = "{validation.notempty.message}")
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @NotNull
    @NotEmpty(message = "{validation.notempty.message}")
    @Size(max = 50)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = WordUtils.capitalize(firstName);
    }

    @NotNull
    @NotEmpty(message = "{validation.notempty.message}")
    @Size(max = 50)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = WordUtils.capitalize(lastName);
    }

    @Email
    @NotEmpty(message = "{validation.notempty.message}")
    @NotNull
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String toString() {

        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("firstName", firstName)
                .append("lastName", lastName)
                .append("username", username)
                .append("password", password)
                .append("confirmPassword", confirmPassword)
                .toString();
    }


}

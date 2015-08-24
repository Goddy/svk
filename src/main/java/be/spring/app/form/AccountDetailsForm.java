package be.spring.app.form;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.lang3.text.WordUtils;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * User: Tom De Dobbeleer
 * Date: 12/13/13
 * Time: 2:52 PM
 * Remarks: none
 */
public class AccountDetailsForm {
    protected String firstName, lastName, username;
    private boolean hasSignInProvider;
    private boolean hasPassword;
    private boolean doodleNotificationMails;

    @NotNull
    @NotEmpty(message = "{validation.notempty.message}")
    @Size(max = 50)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = WordUtils.capitalize(firstName);
    }

    public boolean isDoodleNotificationMails() {
        return doodleNotificationMails;
    }

    public void setDoodleNotificationMails(boolean doodleNotificationMails) {
        this.doodleNotificationMails = doodleNotificationMails;
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
                .toString();
    }

    public boolean isHasSignInProvider() {
        return hasSignInProvider;
    }

    public void setHasSignInProvider(boolean hasSignInProvider) {
        this.hasSignInProvider = hasSignInProvider;
    }

    public boolean isHasPassword() {
        return hasPassword;
    }

    public void setHasPassword(boolean hasPassword) {
        this.hasPassword = hasPassword;
    }
}


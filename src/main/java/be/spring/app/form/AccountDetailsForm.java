package be.spring.app.form;

import be.spring.app.utils.Constants;
import org.apache.commons.lang3.text.WordUtils;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
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
    private boolean newsNotificationMails;

    @NotNull
    @NotEmpty(message = "{validation.notempty.message}")
    @Size(max = 50, min = 2)
    @Pattern(regexp = Constants.NAME_REGEX, message = "{validation.name.mismatch}")
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

    public boolean isNewsNotificationMails() {
        return newsNotificationMails;
    }

    public void setNewsNotificationMails(boolean newsNotificationMails) {
        this.newsNotificationMails = newsNotificationMails;
    }

    @NotNull
    @NotEmpty(message = "{validation.notempty.message}")
    @Size(max = 50, min = 2)
    @Pattern(regexp = Constants.NAME_REGEX, message = "{validation.name.mismatch}")
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


    @Override
    public String toString() {
        return "AccountDetailsForm{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", hasSignInProvider=" + hasSignInProvider +
                ", hasPassword=" + hasPassword +
                ", doodleNotificationMails=" + doodleNotificationMails +
                ", newsNotificationMails=" + newsNotificationMails +
                '}';
    }
}


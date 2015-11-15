package be.spring.app.form;

import org.apache.commons.lang3.text.WordUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * User: Tom De Dobbeleer
 * Date: 12/13/13
 * Time: 2:52 PM
 * Remarks: none
 */
public class AccountProfileForm {
    protected String firstName, lastName, username, phone, mobilePhone;
    private MultipartFile avatar;
    private boolean hasSignInProvider;
    private boolean hasPassword;
    private boolean doodleNotificationMails;
    private boolean newsNotificationMails;

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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = WordUtils.capitalize(lastName);
    }

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public MultipartFile getAvatar() {
        return avatar;
    }

    public void setAvatar(MultipartFile avatar) {
        this.avatar = avatar;
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


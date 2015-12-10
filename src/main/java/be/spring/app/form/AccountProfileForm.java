package be.spring.app.form;

import be.spring.app.data.PositionsEnum;
import org.apache.commons.lang3.text.WordUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * User: Tom De Dobbeleer
 * Date: 12/13/13
 * Time: 2:52 PM
 * Remarks: none
 */
public class AccountProfileForm extends AccountForm {
    private String phone, mobilePhone;
    private MultipartFile avatar;
    private PositionsEnum position;
    private boolean hasSignInProvider;
    private boolean hasPassword;
    private boolean doodleNotificationMails;
    private boolean newsNotificationMails;
    private String avatarUrl;
    private boolean removeAvatar;
    private String address;
    private String postalCode;
    private String city;

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

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public boolean isRemoveAvatar() {
        return removeAvatar;
    }

    public void setRemoveAvatar(boolean removeAvatar) {
        this.removeAvatar = removeAvatar;
    }

    public PositionsEnum getPosition() {
        return position;
    }

    public void setPosition(PositionsEnum position) {
        this.position = position;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = WordUtils.capitalize(address);
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = WordUtils.capitalize(city);
    }

    @Override
    public String toString() {
        return "AccountProfileForm{" +
                "phone='" + phone + '\'' +
                ", mobilePhone='" + mobilePhone + '\'' +
                ", avatar=" + avatar +
                ", position=" + position +
                ", hasSignInProvider=" + hasSignInProvider +
                ", hasPassword=" + hasPassword +
                ", doodleNotificationMails=" + doodleNotificationMails +
                ", newsNotificationMails=" + newsNotificationMails +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", removeAvatar=" + removeAvatar +
                ", address='" + address + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", city='" + city + '\'' +
                "} " + super.toString();
    }
}


package be.spring.app.form;

import be.spring.app.data.SocialMediaEnum;

public class RegistrationForm extends AccountForm {

    protected String password, confirmPassword;
    protected SocialMediaEnum signInProvider;

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

    public SocialMediaEnum getSignInProvider() {
        return signInProvider;
    }

    public void setSignInProvider(SocialMediaEnum signInProvider) {
        this.signInProvider = signInProvider;
    }

    public boolean isNormalRegistration() {
        return signInProvider == null;
    }

    public boolean isSocialSignIn() {
        return signInProvider != null;
    }

    @Override
    public String toString() {
        return "RegistrationForm{" +
                "password=***'" + '\'' +
                ", confirmPassword=***'" + '\'' +
                ", signInProvider=" + signInProvider +
                "} " + super.toString();
    }
}

package be.spring.app.form;

/**
 * Created by u0090265 on 9/11/14.
 */
public class PwdRecoveryForm {
    private String email;
    private String code;
    private String newPassword;
    private String repeatNewPassword;
    private boolean newCode = true;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getRepeatNewPassword() {
        return repeatNewPassword;
    }

    public void setRepeatNewPassword(String repeatNewPassword) {
        this.repeatNewPassword = repeatNewPassword;
    }

    public boolean isNewCode() {
        return newCode;
    }

    public void setNewCode(boolean newCode) {
        this.newCode = newCode;
    }
}

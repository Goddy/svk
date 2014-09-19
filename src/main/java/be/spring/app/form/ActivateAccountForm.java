package be.spring.app.form;

import javax.validation.constraints.NotNull;

/**
 * Created by u0090265 on 9/19/14.
 */
public class ActivateAccountForm {
    private String accountId;
    private boolean sendEmail;

    public boolean isSendEmail() {
        return sendEmail;
    }

    public void setSendEmail(boolean sendEmail) {
        this.sendEmail = sendEmail;
    }

    @NotNull
    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}

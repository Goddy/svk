package be.svk.webapp.form;

import javax.validation.constraints.NotNull;

/**
 * Created by u0090265 on 9/19/14.
 */
public class ActivateAccountForm {
    private long accountId;
    private boolean sendEmail;

    public boolean isSendEmail() {
        return sendEmail;
    }

    public void setSendEmail(boolean sendEmail) {
        this.sendEmail = sendEmail;
    }

    @NotNull
    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }
}

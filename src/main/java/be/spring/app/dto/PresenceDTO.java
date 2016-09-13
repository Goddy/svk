package be.spring.app.dto;

import be.spring.app.model.Presence;

/**
 * Created by u0090265 on 09/09/16.
 */
public class PresenceDTO implements Comparable<PresenceDTO> {
    private AccountDTO account;
    private boolean isEditable;
    private Presence.PresenceType present;

    public PresenceDTO(AccountDTO account, Presence.PresenceType present, boolean isEditable) {
        this.account = account;
        this.present = present;
        this.isEditable = isEditable;
    }

    public AccountDTO getAccount() {
        return account;
    }

    public void setAccount(AccountDTO account) {
        this.account = account;
    }

    public Presence.PresenceType isPresent() {
        return present;
    }

    public void setPresent(Presence.PresenceType present) {
        this.present = present;
    }

    public boolean isEditable() {
        return isEditable;
    }

    public void setEditable(boolean editable) {
        isEditable = editable;
    }

    @Override
    public int compareTo(PresenceDTO o) {
        return account.compareTo(o.account);
    }
}

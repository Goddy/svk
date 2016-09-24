package be.spring.app.dto;

import be.spring.app.model.Presence;

/**
 * Created by u0090265 on 09/09/16.
 */
public class PresenceDTO implements Comparable<PresenceDTO> {
    private AccountDTO account;
    private boolean isEditable;
    private Presence.PresenceType type;

    public PresenceDTO(AccountDTO account, Presence.PresenceType type, boolean isEditable) {
        this.account = account;
        this.type = type;
        this.isEditable = isEditable;
    }

    public Presence.PresenceType getType() {
        return type;
    }

    public void setType(Presence.PresenceType type) {
        this.type = type;
    }

    public AccountDTO getAccount() {
        return account;
    }

    public void setAccount(AccountDTO account) {
        this.account = account;
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
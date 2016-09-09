package be.spring.app.dto;

import be.spring.app.model.Presence;

/**
 * Created by u0090265 on 09/09/16.
 */
public class PresenceDTO implements Comparable<PresenceDTO> {
    private AccountDTO account;
    private Presence.PresenceType present;

    public PresenceDTO(AccountDTO account, Presence.PresenceType present) {
        this.account = account;
        this.present = present;
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

    @Override
    public int compareTo(PresenceDTO o) {
        return account.compareTo(o.account);
    }
}

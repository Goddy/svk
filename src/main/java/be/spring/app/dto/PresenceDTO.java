package be.spring.app.dto;

/**
 * Created by u0090265 on 09/09/16.
 */
public class PresenceDTO implements Comparable<PresenceDTO> {
    private AccountDTO account;
    private boolean present;

    public PresenceDTO(AccountDTO account, boolean present) {
        this.account = account;
        this.present = present;
    }

    public AccountDTO getAccount() {
        return account;
    }

    public void setAccount(AccountDTO account) {
        this.account = account;
    }

    public boolean isPresent() {
        return present;
    }

    public void setPresent(boolean present) {
        this.present = present;
    }

    @Override
    public int compareTo(PresenceDTO o) {
        return account.compareTo(o.account);
    }
}
